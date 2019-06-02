package me.passin.loadknife.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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

    private Map<Class<? extends Callback>, ServiceView> mServiceViewMap = new ConcurrentHashMap<>();
    private Context context;
    private Callback.OnReloadListener onReloadListener;
    private Class<? extends Callback> curCallbackClass;

    public LoadLayout(@NonNull Context context, Callback.OnReloadListener onReloadListener) {
        this(context);
        this.context = context;
        this.onReloadListener = onReloadListener;
    }

    public LoadLayout(@NonNull Context context) {
        super(context);
    }

    /**
     * @hide
     */
    public static void addCallback(Callback... callbacks) {
        for (Callback callback : callbacks) {
            LoadLayout.callbacks.put(callback.getClass(), callback);
        }
    }

    public void initSuccessView(View view) {
        mServiceViewMap.put(SuccessCallback.class, new ServiceView(view));
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

    private void showCallbackView(Class<? extends Callback> preCallbackClass) {
        if (curCallbackClass == preCallbackClass) {
            return;
        }
        if (getChildCount() > 1) {
            Callback curCallback = getCallback(curCallbackClass);
            curCallback.onDetach(context, getServiceView(curCallbackClass).getViewHelper());
            removeViewAt(1);
        }
        ServiceView preServiceView = getServiceView(preCallbackClass);
        if (preCallbackClass == SuccessCallback.class) {
            preServiceView.setVisibility(true);
        } else {
            Callback preCallback = getCallback(preCallbackClass);
            addView(preServiceView.getRootView());
            getServiceView(SuccessCallback.class).setVisibility(preCallback.successViewVisible());
            preCallback.onAttach(context, preServiceView.getViewHelper());
        }

        curCallbackClass = preCallbackClass;
    }


    private Callback getCallback(Class<? extends Callback> callbackClass) {
        Callback callback = callbacks.get(callbackClass);
        if (callback == null) {
            try {
                callback = callbackClass.newInstance();
                callbacks.put(callbackClass, callback);
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
        return callback;
    }


    private ServiceView getServiceView(Class<? extends Callback> callbackClass) {
        ServiceView serviceView = mServiceViewMap.get(callbackClass);
        if (serviceView == null) {
            Callback callback = getCallback(callbackClass);
            // 构建 rootView
            View rootView = View.inflate(context, callback.getLayoutId(), null);
            ViewHelper viewHelper = new ViewHelper(rootView);

            serviceView = new ServiceView(rootView, viewHelper, onReloadListener);
            callback.onViewCreate(context, viewHelper);
            mServiceViewMap.put(callbackClass, serviceView);
        }
        return serviceView;
    }

    public Class<? extends Callback> getCurrentCallback() {
        return curCallbackClass;
    }

    public ViewHelper getViewHelper(Class<? extends Callback> callback) {
        return getServiceView(callback).getViewHelper();
    }

}
