package me.passin.loadknife.sample.view;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.core.LoadKnife;
import me.passin.loadknife.core.LoadService;
import me.passin.loadknife.sample.PostUtil;
import me.passin.loadknife.sample.R;
import me.passin.loadknife.sample.callback.AnimateCallback;
import me.passin.loadknife.sample.callback.EmptyCallback;


public class AnimateActivity extends AppCompatActivity {

    private LoadService loadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        LoadKnife loadSir = LoadKnife.newBuilder()
                .defaultCallback(AnimateCallback.class)
                .build();
        loadService = loadSir.register(this, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loadService.showCallback(AnimateCallback.class);
                        SystemClock.sleep(1000);
                        loadService.showSuccess();
                    }
                }).start();
            }
        });
        PostUtil.postCallbackDelayed(loadService, EmptyCallback.class, 1000);
    }
}
