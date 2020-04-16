package me.passin.loadknife.callback;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import me.passin.loadknife.core.ViewHelper;

/**
 * @author: zbb 33775
 * @date: 2019/3/20 21:39
 * @desc: Callback 只作为一个数据的转接者，是被多个界面共用的，
 *         因此实现类的变量是共用的。
 */
public abstract class Callback {

    public abstract @LayoutRes
    int getLayoutId();

    /**
     * Context 和 ViewGroup 都不可作为成员变量。
     */
    @Nullable
    public View onCreateView(Context context, @Nullable ViewGroup container) {
        return null;
    }

    /**
     * 添加到 LoadLayout 后触发，Context 和 ViewHelper 都不可作为成员变量。
     */
    public void onAttach(Context context, ViewHelper viewHelper) {

    }

    /**
     * 从 LoadLayout 移除前触发，Context 和 ViewHelper 都不可作为成员变量。
     */
    public void onDetach(Context context, ViewHelper viewHelper) {

    }

    /**
     * @param view 所切换的视图
     * @return true：拦截
     */
    public boolean onReloadEvent(View view) {
        return false;
    }

    /**
     * @return true：和 successView 一起显示
     *         false： 单独显示
     */
    public boolean successViewVisible() {
        return false;
    }

    public interface OnReloadListener {

        void onReload(Callback callback, View v);

    }

}