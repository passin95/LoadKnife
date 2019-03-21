package me.passin.loadknife.callback;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import me.passin.loadknife.core.ViewHelper;

/**
 * @author: zbb 33775
 * @date: 2019/3/20 21:39
 * @desc:
 */
public interface Callback {

     @LayoutRes int getLayoutId();

     /**
      * 初始化 mRootView 成功后调用，Context 和 ViewHelper 都不可作为成员变量。
      */
     void onViewCreate(Context context, ViewHelper viewHelper);

     /**
      * 添加到 LoadLayout 后触发，Context 和 ViewHelper 都不可作为成员变量。
      */
     void onAttach(Context context, ViewHelper viewHelper);

     /**
      * 从 LoadLayout 移除前触发，Context 和 ViewHelper 都不可作为成员变量。
      */
     void onDetach(Context context, ViewHelper viewHelper);

     /**
      * @return true：和 successView 一起显示
      *          false： 单独显示
      */
     boolean successViewVisible();

     interface OnReloadListener {

          void onReload(View v);
     }
}
