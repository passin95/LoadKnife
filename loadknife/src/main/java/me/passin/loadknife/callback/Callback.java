package me.passin.loadknife.callback;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import me.passin.loadknife.core.ViewHelper;

/**
 * @author: zbb 33775
 * @date: 2019/3/20 21:39
 * @desc: Callback 只作为一个数据的转接者，是被多个界面共用的，
 *         因此实现类的变量是共用的。
 */
public abstract class Callback {

    /**
     * Context 和 ViewGroup 都不可作为成员变量。
     */
    @NonNull
    public abstract View onCreateView(@NonNull Context context, @NonNull ViewGroup container);

    /**
     * 添加到 LoadLayout 后触发，Context 和 ViewHelper 都不可作为成员变量。
     */
    public void onAttach(@NonNull Context context, @NonNull ViewHelper viewHelper) {

    }

    /**
     * 从 LoadLayout 移除前或脱离 window 时触发，Context 和 ViewHelper 都不可作为成员变量。
     */
    public void onDetach(@NonNull Context context, @NonNull ViewHelper viewHelper) {

    }

    /**
     * @return true：拦截
     */
    public boolean onInterceptReloadEvent(@NonNull ViewHelper viewHelper) {
        return false;
    }

    public int successViewVisibility() {
        return View.INVISIBLE;
    }

    public interface OnReloadListener {

        void onReload(@NonNull Callback callback, @NonNull View v);

    }

}