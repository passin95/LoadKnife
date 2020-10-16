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
import me.passin.loadknife.sample.callback.PlaceholderCallback;

public class PlaceholderActivity extends AppCompatActivity {

    private LoadService loadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeholder);
        LoadKnife loadKnife = LoadKnife.newBuilder()
                .defaultCallback(PlaceholderCallback.class)
                .build();
        loadService = loadKnife.register(this, new Callback.OnReloadListener() {
            @Override
            public void onReload(Callback callback, View v) {
                //do retry logic...
            }
        });
        loadService.showDefault();
        PostUtil.postSuccessDelayed(loadService, 1000);
    }

}
