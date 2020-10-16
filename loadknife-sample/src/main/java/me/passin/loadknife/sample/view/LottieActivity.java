package me.passin.loadknife.sample.view;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.core.LoadKnife;
import me.passin.loadknife.core.LoadService;
import me.passin.loadknife.sample.PostUtil;
import me.passin.loadknife.sample.R;
import me.passin.loadknife.sample.callback.LoadingCallback;
import me.passin.loadknife.sample.callback.LottieEmptyCallback;
import me.passin.loadknife.sample.callback.LottieLoadingCallback;

public class LottieActivity extends AppCompatActivity {

    private LoadService loadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        LoadKnife loadSir = LoadKnife.newBuilder()
                .defaultCallback(LoadingCallback.class)
                .build();
        loadService = loadSir.register(this, new Callback.OnReloadListener() {
            @Override
            public void onReload(Callback callback, View v) {
                loadService.showCallback(LottieLoadingCallback.class);
                PostUtil.postSuccessDelayed(loadService, 8000);
            }
        });
        loadService.showDefault();
        PostUtil.postCallbackDelayed(loadService, LottieEmptyCallback.class);
    }

}
