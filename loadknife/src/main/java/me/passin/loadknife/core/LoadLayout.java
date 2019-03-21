package me.passin.loadknife.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import java.util.HashMap;
import java.util.Map;
import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.callback.SuccessCallback;
import me.passin.loadknife.utils.LoadKnifeUtils;

/**
 * @author: zbb 33775
 * @date: 2019/3/19 21:59
 * @hide
 */
public class LoadLayout extends FrameLayout {

    private static final Map<Class<? extends Callback>, Callback> callbacks = new HashMap<>();
    private static final SuccessCallback SUCCESS_CALLBACK = new SuccessCallback();

    static {
        callbacks.put(SuccessCallback.class, SUCCESS_CALLBACK);
    }

    private Map<Class<? extends Callback>, ServiceView> mServiceViewMap = new HashMap<>();
    private Context context;
    private Callback.OnReloadListener onReloadListener;
    private Class<? extends Callback> curCallback;

    public LoadLayout(@NonNull Context context) {
        super(context);
    }

    public LoadLayout(@NonNull Context context, Callback.OnReloadListener onReloadListener) {
        this(context);
        this.context = context;
        this.onReloadListener = onReloadListener;
    }

    public void initSuccessView(View view) {
        mServiceViewMap.put(SuccessCallback.class, new ServiceView(view, SUCCESS_CALLBACK));
        addView(view);
    }

    public void showCallback(final Class<? extends Callback> callback) {
        if (LoadKnifeUtils.isMainThread()) {
            showCallbackView(callback);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showCallbackView(callback);
                }
            });
        }
    }

    public Class<? extends Callback> getCurrentCallback() {
        return curCallback;
    }

    private void showCallbackView(Class<? extends Callback> callback) {
        if (curCallback == callback) {
            return;
        }
        if (getChildCount() > 1) {
            ServiceView curServiceView = getServiceView(curCallback);
            curServiceView.onDetach();
            removeViewAt(1);
        }
        ServiceView preServiceView = getServiceView(callback);
        if (callback == SuccessCallback.class) {
            preServiceView.setVisibility(true);
        } else {
            addView(preServiceView.getRootView());
            getServiceView(SuccessCallback.class).setVisibility(preServiceView.successViewVisible());
            preServiceView.onAttach();
        }

        curCallback = callback;
    }

    private ServiceView getServiceView(Class<? extends Callback> callbackClass) {
        ServiceView serviceView = mServiceViewMap.get(callbackClass);
        if (serviceView == null) {
            Callback callback = callbacks.get(callbackClass);
            if (callback == null) {
                try {
                    callback = callbackClass.newInstance();
                    // 构建 rootView
                    View rootView = View.inflate(context, callback.getLayoutId(), null);
                    ViewHelper viewHelper = new ViewHelper(rootView);

                    serviceView = new ServiceView(rootView, viewHelper, callback, onReloadListener);
                    serviceView.onViewCreate();
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException(
                            "Please provide a constructor without parameters in " + callbackClass.getSimpleName()
                                    + ",or add from LoadKnife.Builder.addCallback()");
                } catch (InstantiationException e) {
                    throw new IllegalArgumentException(
                            "Please provide a constructor without parameters in " + callbackClass.getSimpleName()
                                    + ",or add from LoadKnife.Builder.addCallback()");
                }
            }
        }

        return serviceView;
    }

    public void setCallBack(@NonNull Class<? extends Callback> callbackClass, @NonNull Transform transport) {
        ServiceView serviceView = getServiceView(callbackClass);
        transport.modify(context, serviceView.getViewHelper());
    }

    /**
     * @hide
     */
    public static void addCallback(Callback... callbacks) {
        for (Callback callback : callbacks) {
            LoadLayout.callbacks.put(callback.getClass(), callback);
        }
    }

}
