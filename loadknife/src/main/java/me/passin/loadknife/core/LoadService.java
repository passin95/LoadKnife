package me.passin.loadknife.core;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.callback.Callback.OnReloadListener;
import me.passin.loadknife.callback.SuccessCallback;

/**
 * @author: zbb 33775
 * @date: 2019/3/19 15:33
 * @desc:
 */
public class LoadService {

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
        mLoadLayout.initSuccessView(realView);
        if (parentView != null) {
            parentView.addView(mLoadLayout, childIndex, realView.getLayoutParams());
        }
    }

    public void showSuccess() {
        mLoadLayout.showCallback(SuccessCallback.class);
    }

    public void showDefault() {
        if (mDefaultCallback != null) {
            mLoadLayout.showCallback(mDefaultCallback);
        }
    }

    public void showCallback(Object states) {
        try {
            Class<? extends Callback> callback = mLoadKnife.callbackConverter(states);
            if (callback != null) {
                mLoadLayout.showCallback(callback);
                return;
            }
            throw new Exception("converter " + states.getClass().getSimpleName() + " fail.");
        } catch (Exception e) {
            if (LoadKnife.isDebug) {
                Log.e("LoadKnife", e.toString());
            }
            if (mLoadKnife.mErrorCallback != null) {
                mLoadLayout.showCallback(mLoadKnife.mErrorCallback);
            }
        }
    }

    public LoadLayout getLoadLayout() {
        return mLoadLayout;
    }

    public void showCallback(Class<? extends Callback> callback) {
        mLoadLayout.showCallback(callback);
    }

    public LoadService setCallBack(Class<? extends Callback> callback, Transform transport) {
        mLoadLayout.setCallBack(callback, transport);
        return this;
    }


}
