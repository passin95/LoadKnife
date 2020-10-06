package me.passin.loadknife.core;

import android.util.SparseArray;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.collection.ArrayMap;
import java.util.Map;

/**
 * @author: zbb 33775
 * @date: 2019/3/20 22:06
 * @desc:
 */
public class ViewHelper {

    private View mRootView;
    private SparseArray<View> mViews;
    private Map<String, Object> mDataMap;

    ViewHelper(View view) {
        mRootView = view;
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
            mDataMap = new ArrayMap<>();
        }
        mDataMap.put(key, value);
    }

    /**
     * 用于在不同状态中传递数据
     */
    public Object getData(String key) {
        if (mDataMap == null) {
            return null;
        }
        return mDataMap.get(key);
    }

    public View getRootView() {
        return mRootView;
    }

    public void setVisibility(int visibility) {
        mRootView.setVisibility(visibility);
    }

}
