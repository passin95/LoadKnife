package me.passin.loadknife.core;

import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import java.util.HashMap;
import java.util.Map;
import me.passin.loadknife.callback.Callback.OnReloadListener;

/**
 * @author: zbb 33775
 * @date: 2019/3/20 22:06
 * @desc:
 */
public class ViewHelper {

    private View mRootView;
    private SparseArray<View> mViews;
    private OnReloadListener mOnReloadListener;
    private Map<String, Object> mDataMap;

    ViewHelper(View view) {
        this(view, null);
    }

    ViewHelper(View view, OnReloadListener onReloadListener) {
        mRootView = view;
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

    public <T extends View> T getView(@IdRes int viewId) {
        if (mViews == null) {
            mViews = new SparseArray<>();
        }
        View view = mViews.get(viewId);
        if (null == view) {
            view = mRootView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 用于在 Callback 的生命周期中传递数据。
     */
    public void putData(String key, Object value) {
        if (mDataMap == null) {
            mDataMap = new HashMap();
        }
        mDataMap.put(key, value);
    }

    /**
     * 用于在不同状态中传递数据
     */
    public Object getData(String key) {
        if (mDataMap == null) {
            mDataMap = new HashMap();
        }
        return mDataMap.get(key);
    }

    public View getRootView() {
        return mRootView;
    }

    public void setVisibility(boolean visibility) {
        if (visibility) {
            mRootView.setVisibility(View.VISIBLE);
        } else {
            mRootView.setVisibility(View.INVISIBLE);
        }
    }

}
