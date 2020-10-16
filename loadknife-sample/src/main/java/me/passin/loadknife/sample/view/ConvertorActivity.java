package me.passin.loadknife.sample.view;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.callback.Callback.OnReloadListener;
import me.passin.loadknife.core.LoadKnife;
import me.passin.loadknife.core.LoadService;
import me.passin.loadknife.sample.PostUtil;
import me.passin.loadknife.sample.R;
import me.passin.loadknife.sample.convertor.ViewState;

/**
 * @author: zbb 33775
 * @date: 2019/3/21 15:18
 * @desc:
 */
public class ConvertorActivity extends AppCompatActivity {

    private LoadService mLoadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        mLoadService = LoadKnife.getDefault().register(this, new OnReloadListener() {
            @Override
            public void onReload(@NonNull Callback callback,@NonNull View v) {
                mLoadService.showCallback(ViewState.UNKNOWN);
                PostUtil.postSuccessDelayed(mLoadService, 4000);
            }
        });
        mLoadService.showDefault();
        PostUtil.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadService.showCallback(ViewState.ERROR);
            }
        }, 2000);
    }

}
