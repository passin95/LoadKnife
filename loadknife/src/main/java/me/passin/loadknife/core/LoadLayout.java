package me.passin.loadknife.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import java.util.HashMap;
import java.util.Map;
import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.callback.Callback.OnReloadListener;
import me.passin.loadknife.callback.SuccessCallback;
import me.passin.loadknife.utils.LoadKnifeUtils;

/**
 * @author: zbb 33775
 * @date: 2019/3/19 21:59
 */
public class LoadLayout extends FrameLayout {

    private static final Map<Class<? extends Callback>, Callback> CALLBACKS = new HashMap<>();

    private Map<Class<? extends Callback>, ViewHelper> mServiceViewMap = new ArrayMap<>();
    private Callback.OnReloadListener mOnReloadListener;
    private Class<? extends Callback> mCurCallbackClass;
    private ViewHelper mSuccessViewHelper;

    public LoadLayout(@NonNull Context context, View realView) {
        super(context);
        mSuccessViewHelper = new ViewHelper(realView);
        mServiceViewMap.put(SuccessCallback.class, mSuccessViewHelper);
    }

    @MainThread
    static Callback addCallback(Callback callback) {
        return LoadLayout.CALLBACKS.put(callback.getClass(), callback);
    }

    @MainThread
    @Nullable
    static Callback removeCallback(Class<? extends Callback> callbackClass) {
        return LoadLayout.CALLBACKS.remove(callbackClass);
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
        if (getChildCount() > 1) {
            Callback curCallback = getCallback(mCurCallbackClass);
            curCallback.onDetach(getContext(), getViewHelper(mCurCallbackClass));
            removeViewAt(1);
        }
        if (preCallbackClass == SuccessCallback.class) {
            mSuccessViewHelper.setVisibility(View.VISIBLE);
        } else {
            ViewHelper preServiceView = getViewHelper(preCallbackClass);
            Callback preCallback = getCallback(preCallbackClass);
            addView(preServiceView.getRootView());
            mSuccessViewHelper.setVisibility(preCallback.successViewVisibility());
            preCallback.onAttach(getContext(), preServiceView);
        }

        mCurCallbackClass = preCallbackClass;
    }

    private Callback getCallback(Class<? extends Callback> callbackClass) {
        Callback callback = CALLBACKS.get(callbackClass);
        if (callback == null) {
            try {
                callback = callbackClass.newInstance();
                CALLBACKS.put(callbackClass, callback);
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

    @MainThread
    public ViewHelper getViewHelper(Class<? extends Callback> callbackClass) {
        ViewHelper viewHelper = mServiceViewMap.get(callbackClass);
        if (viewHelper == null) {
            final Callback callback = getCallback(callbackClass);
            // 构建 rootView
            View rootView = callback.onCreateView(getContext(), this);
            if (rootView == null) {
                rootView = LayoutInflater.from(getContext()).inflate(callback.getLayoutId(), this, false);
            }

            viewHelper = new ViewHelper(rootView);
            if (mOnReloadListener != null) {
                rootView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback.onReloadEvent(v)) {
                            return;
                        }
                        mOnReloadListener.onReload(callback, v);
                    }
                });
            }
            mServiceViewMap.put(callbackClass, viewHelper);
        }
        return viewHelper;
    }

    Class<? extends Callback> getCurrentCallback() {
        return mCurCallbackClass;
    }

    public void setOnReloadListener(OnReloadListener onReloadListener) {
        mOnReloadListener = onReloadListener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mCurCallbackClass != null && mCurCallbackClass != SuccessCallback.class) {
            Callback curCallback = getCallback(mCurCallbackClass);
            curCallback.onDetach(getContext(), getViewHelper(mCurCallbackClass));
        }
    }

}
