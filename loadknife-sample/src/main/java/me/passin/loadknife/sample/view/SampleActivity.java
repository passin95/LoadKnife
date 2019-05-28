package me.passin.loadknife.sample.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import me.passin.loadknife.callback.Callback.OnReloadListener;
import me.passin.loadknife.core.LoadKnife;
import me.passin.loadknife.core.LoadService;
import me.passin.loadknife.core.Transform;
import me.passin.loadknife.core.ViewHelper;
import me.passin.loadknife.sample.PostUtil;
import me.passin.loadknife.sample.R;
import me.passin.loadknife.sample.callback.ErrorCallback;

/**
 * @author: zbb 33775
 * @date: 2019/3/21 14:29
 * @desc:
 */
public class SampleActivity extends AppCompatActivity {

    private LoadService mLoadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        mLoadService = LoadKnife.getDefault().register(this, new OnReloadListener() {
            @Override
            public void onReload(View v) {
                mLoadService.showDefault();
                PostUtil.postSuccessDelayed(mLoadService);
            }
        });
        mLoadService.setCallBack(ErrorCallback.class, new Transform() {
            @Override
            public void modify(Context context, ViewHelper viewHelper) {
                viewHelper.setText(R.id.tv_error, "I am modify error")
                        .setTextColor(R.id.tv_error, Color.RED);
            }
        });
        PostUtil.postCallbackDelayed(mLoadService, ErrorCallback.class);
        ViewHelper rootViewHelper = mLoadService.getViewHelper(ErrorCallback.class);
        rootViewHelper.setText(R.id.tv_error_hint, "I am modify error hint")
                .setTextColor(R.id.tv_error_hint, Color.BLUE);
    }

}
