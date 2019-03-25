package me.passin.loadknife.core;

import android.view.View;
import android.view.View.OnClickListener;
import me.passin.loadknife.callback.Callback.OnReloadListener;

/**
 * @author: zbb 33775
 * @date: 2019/3/20 22:06
 * @desc:
 */
public class ServiceView {

    private View mRootView;
    private OnReloadListener mOnReloadListener;
    private ViewHelper mViewHelper;

    ServiceView(View view) {
        this(view, new ViewHelper(view), null);
    }

    ServiceView(View view, ViewHelper viewHelper,OnReloadListener onReloadListener) {
        mRootView = view;
        mViewHelper = viewHelper;
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
