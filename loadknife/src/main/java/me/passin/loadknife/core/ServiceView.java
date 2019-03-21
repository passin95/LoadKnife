package me.passin.loadknife.core;

import android.view.View;
import android.view.View.OnClickListener;
import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.callback.Callback.OnReloadListener;

/**
 * @author: zbb 33775
 * @date: 2019/3/20 22:06
 * @desc:
 */
public class ServiceView {

    private View mRootView;
    private Callback mCallback;
    private OnReloadListener mOnReloadListener;
    private ViewHelper mViewHelper;

    ServiceView(View view, Callback callback) {
        this(view, new ViewHelper(view), callback, null);
    }

    ServiceView(View view, ViewHelper viewHelper, Callback callback, OnReloadListener onReloadListener) {
        mRootView = view;
        mViewHelper = viewHelper;
        mCallback = callback;
        mOnReloadListener = onReloadListener;
        init();
    }

    private void init() {
        if (mOnReloadListener != null) {
            mRootView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnReloadListener.onReload(v);
                }
            });
        }

    }


    public View getRootView() {
        return mRootView;
    }

    /**
     * 初始化 mRootView 成功后调用
     */
    void onViewCreate() {
        mCallback.onViewCreate(mRootView.getContext(), mViewHelper);
    }

    /**
     * 添加到 LoadLayout 后触发
     */
    void onAttach() {
        mCallback.onAttach(mRootView.getContext(), mViewHelper);
    }

    /**
     * 从 LoadLayout 移除前触发
     */
    void onDetach() {
        mCallback.onDetach(mRootView.getContext(),mViewHelper);
    }

    /**
     * @return true：和 successView 一起显示
     * false： 单独显示
     */
    boolean successViewVisible() {
        return mCallback.successViewVisible();
    }

    public OnReloadListener getOnReloadListener() {
        return mOnReloadListener;
    }

    public ViewHelper getViewHelper() {
        return mViewHelper;
    }

    public void setVisibility(boolean visibility) {
        if (visibility) {
            mRootView.setVisibility(View.VISIBLE);
        } else {
            mRootView.setVisibility(View.INVISIBLE);
        }
    }
}
