package me.passin.loadknife.core;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    LoadService(LoadKnife loadKnife, Object target,
            Class<? extends Callback> defaultCallback, OnReloadListener onReloadListener) {
        mLoadKnife = loadKnife;
        mDefaultCallback = defaultCallback;
        mLoadLayout = mLoadKnife.targetAdapter(target);
        mLoadLayout.setOnReloadListener(onReloadListener);
        showDefault();
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
        Class<? extends Callback> callback = mLoadKnife.callbackAdapter(state);
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
        Class<? extends Callback> callback = mLoadKnife.callbackAdapter(state);

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
