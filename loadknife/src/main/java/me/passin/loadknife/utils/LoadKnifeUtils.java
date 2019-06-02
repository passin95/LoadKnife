package me.passin.loadknife.utils;

import android.os.Looper;

/**
 * @author: zbb 33775
 * @date: 2019/3/19 17:36
 * @desc:
 */
public class LoadKnifeUtils {

    public static boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
