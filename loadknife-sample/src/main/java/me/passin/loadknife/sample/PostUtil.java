package me.passin.loadknife.sample;

import android.os.Handler;
import android.os.Looper;
import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.core.LoadService;

/**
 * @author: zbb 33775
 * @date: 2019/3/21 14:03
 * @desc:
 */
public class PostUtil {

    private static Handler H = new Handler(Looper.getMainLooper());

    private static final int DELAY_TIME = 1500;

    public static void postCallbackDelayed(final LoadService loadService, final Class<? extends Callback> clazz) {
        postCallbackDelayed(loadService, clazz, DELAY_TIME);
    }

    public static void postCallbackDelayed(final LoadService loadService, final Class<? extends Callback> clazz,
            long delay) {
        H.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadService.showCallback(clazz);
            }
        }, delay);
    }

    public static void postSuccessDelayed(final LoadService loadService) {
        postSuccessDelayed(loadService, DELAY_TIME);
    }

    public static void postSuccessDelayed(final LoadService loadService, long delay) {
        H.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadService.showSuccess();
            }
        }, delay);
    }

    public static void post(Runnable runnable) {
        H.post(runnable);
    }


    public static void postDelayed(Runnable runnable, long delayMillis) {
        H.postDelayed(runnable, delayMillis);
    }
}
