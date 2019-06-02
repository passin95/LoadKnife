package me.passin.loadknife.sample.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
            public void onReload(View v) {
                mLoadService.showCallback(ViewState.UNKNOWN);
                PostUtil.postSuccessDelayed(mLoadService, 8000);
            }
        });
        PostUtil.post(new Runnable() {
            @Override
            public void run() {
                mLoadService.showCallback(ViewState.ERROR);
            }
        });
    }
}
