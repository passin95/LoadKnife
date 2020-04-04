package me.passin.loadknife.core;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.callback.Callback.OnReloadListener;
import me.passin.loadknife.callback.SuccessCallback;

/**
 * @author: zbb 33775
 * @date: 2019/3/19 15:33
 * @desc:
 */
public class LoadService {

    public static Class constraintLayoutClass;

    static {
        try {
            constraintLayoutClass = Class.forName("android.support.constraint.ConstraintLayout");
        } catch (ClassNotFoundException e) {
            try {
                constraintLayoutClass = Class.forName("androidx.constraintlayout.widget.ConstraintLayout");
            } catch (ClassNotFoundException ex) {
            }
        }
    }

    private LoadKnife mLoadKnife;
    private LoadLayout mLoadLayout;
    private Class<? extends Callback> mDefaultCallback;

    LoadService(LoadKnife loadKnife, Object target, Class<? extends Callback> defaultCallback,
            OnReloadListener onReloadListener) {
        mLoadKnife = loadKnife;
        mDefaultCallback = defaultCallback;
        initLoadLayout(target, onReloadListener);
        showDefault();
    }

    private void initLoadLayout(Object target, OnReloadListener onReloadListener) {
        ViewGroup parentView;
        // 被替换的 view
        View realView;
        Context context;
        int childIndex = 0;

        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            context = activity;
            parentView = activity.findViewById(android.R.id.content);
            realView = parentView.getChildAt(0);
            parentView.removeViewAt(0);
        } else if (target instanceof View) {
            realView = (View) target;
            context = realView.getContext();
            parentView = (ViewGroup) (realView.getParent());
            if (parentView != null) {
                // childIndex 不会返回 -1.
                childIndex = parentView.indexOfChild(realView);
                parentView.removeViewAt(childIndex);
            }
        } else {
            throw new IllegalArgumentException("The target must be within Activity, Fragment, View.");
        }

        mLoadLayout = new LoadLayout(context, onReloadListener);
        LayoutParams realViewLayoutParams = realView.getLayoutParams();
        mLoadLayout.initSuccessView(realView);

        if (parentView != null) {
            if (mLoadKnife.mIsEnableUseChildViewId && parentView instanceof RelativeLayout ||
                    (constraintLayoutClass != null && constraintLayoutClass.isInstance(parentView))) {
                mLoadLayout.setId(realView.getId());
            }

            parentView.addView(mLoadLayout, childIndex, realViewLayoutParams);
        }
    }

    public void showDefault() {
        if (mDefaultCallback != null) {
            mLoadLayout.showCallback(mDefaultCallback);
        }
    }

    public void showSuccess() {
        mLoadLayout.showCallback(SuccessCallback.class);
    }

    public void showCallback(@NonNull Object state) {
        Class<? extends Callback> callback = mLoadKnife.callbackConverter(state);
        if (callback != null) {
            mLoadLayout.showCallback(callback);
        } else {
            mLoadLayout.showCallback(mLoadKnife.mErrorCallback);
        }
    }

    public void showCallback(@NonNull Class<? extends Callback> callback) {
        mLoadLayout.showCallback(callback);
    }

    /**
     * 当传入的 state 无法被转换为 Callback 时 返回 null。
     */
    @Nullable
    public ViewHelper getViewHelper(@NonNull Object state) {
        Class<? extends Callback> callback = mLoadKnife.callbackConverter(state);

        if (callback != null) {
            return mLoadLayout.getViewHelper(callback);
        }
        return null;
    }

    @Nullable
    @MainThread
    public ViewHelper getViewHelper(@NonNull Class<? extends Callback> callback) {
        return mLoadLayout.getViewHelper(callback);
    }

    public LoadLayout getLoadLayout() {
        return mLoadLayout;
    }

    public Class<? extends Callback> getCurrentCallback() {
        return mLoadLayout.getCurrentCallback();
    }

}
