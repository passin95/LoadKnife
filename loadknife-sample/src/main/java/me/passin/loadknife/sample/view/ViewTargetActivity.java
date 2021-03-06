package me.passin.loadknife.sample.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.core.LoadKnife;
import me.passin.loadknife.core.LoadService;
import me.passin.loadknife.sample.PostUtil;
import me.passin.loadknife.sample.R;
import me.passin.loadknife.sample.callback.LoadingCallback;
import me.passin.loadknife.sample.callback.TimeoutCallback;


public class ViewTargetActivity extends AppCompatActivity {

    private LoadService loadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ImageView imageView = findViewById(R.id.iv_img);
        LoadKnife loadSir = new LoadKnife.Builder()
                .defaultCallback(LoadingCallback.class)
                .build();
        loadService = loadSir.register(imageView, new Callback.OnReloadListener() {
            @Override
            public void onReload(Callback callback, View v) {
                loadService.showCallback(LoadingCallback.class);

                PostUtil.postSuccessDelayed(loadService);
            }
        });
        loadService.showDefault();
        PostUtil.postCallbackDelayed(loadService, TimeoutCallback.class);
    }

}
