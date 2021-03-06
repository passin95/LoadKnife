package me.passin.loadknife.sample;

import android.app.Application;
import android.os.StrictMode;
import me.passin.loadknife.core.LoadKnife;
import me.passin.loadknife.sample.callback.LoadingCallback;
import me.passin.loadknife.sample.convertor.StateConvertor;

/**
 * @author: zbb 33775
 * @date: 2019/3/19 15:55
 * @desc:
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LoadKnife.newBuilder()
                .defaultCallback(LoadingCallback.class)
                .addCallbackConvertor(StateConvertor.create())
                .initializeDefault();
        StrictMode.enableDefaults();
    }
}
