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
 */
public class LoadLayout extends FrameLayout {

    private static final Map<Class<? extends Callback>, Callback> callbacks = new HashMap<>();

    private Map<Class<? extends Callback>, ServiceView> mServiceViewMap = new ConcurrentHashMap<>();
    private Context mContext;
    private Callback.OnReloadListener mOnReloadListener;
    private Class<? extends Callback> mCurCallbackClass;
    private boolean mIsDetach;

    LoadLayout(@NonNull Context context) {
        super(context);
    }

    public LoadLayout(@NonNull Context context, Callback.OnReloadListener onReloadListener) {
        this(context);
        this.mContext = context;
        this.mOnReloadListener = onReloadListener;
    }

    static void addCallback(Callback... callbacks) {
        for (Callback callback : callbacks) {
            LoadLayout.callbacks.put(callback.getClass(), callback);
        }
    }

    void initSuccessView(View view) {
        mServiceViewMap.put(SuccessCallback.class, new ServiceView(view));
        addView(view);
    }

    void showCallback(final Class<? extends Callback> callback) {
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
        if (mCurCallbackClass == preCallbackClass) {
            return;
        }
        if (mIsDetach) {
            return;
        }
        if (getChildCount() > 1) {
            Callback curCallback = getCallback(mCurCallbackClass);
            curCallback.onDetach(mContext, getServiceView(mCurCallbackClass).getViewHelper());
            removeViewAt(1);
        }
        ServiceView preServiceView = getServiceView(preCallbackClass);
        if (preCallbackClass == SuccessCallback.class) {
            preServiceView.setVisibility(true);
        } else {
            Callback preCallback = getCallback(preCallbackClass);
            addView(preServiceView.getRootView());
            getServiceView(SuccessCallback.class).setVisibility(preCallback.successViewVisible());
            preCallback.onAttach(mContext, preServiceView.getViewHelper());
        }

        mCurCallbackClass = preCallbackClass;
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
            View rootView = View.inflate(mContext, callback.getLayoutId(), null);
            ViewHelper viewHelper = new ViewHelper(rootView);

            serviceView = new ServiceView(rootView, viewHelper, mOnReloadListener);
            callback.onViewCreate(mContext, viewHelper);
            mServiceViewMap.put(callbackClass, serviceView);
        }
        return serviceView;
    }

    Class<? extends Callback> getCurrentCallback() {
        return mCurCallbackClass;
    }

    ViewHelper getViewHelper(Class<? extends Callback> callback) {
        return getServiceView(callback).getViewHelper();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mIsDetach = false;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mIsDetach = true;
    }
}
