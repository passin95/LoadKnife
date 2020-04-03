package me.passin.loadknife.sample.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import me.passin.loadknife.callback.Callback.OnReloadListener;
import me.passin.loadknife.core.LoadKnife;
import me.passin.loadknife.core.LoadService;
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
        PostUtil.postCallbackDelayed(mLoadService, ErrorCallback.class);
        ViewHelper rootViewHelper = mLoadService.getViewHelper(ErrorCallback.class);
        TextView tvErrorHint = rootViewHelper.getView(R.id.tv_error_hint);
        tvErrorHint.setText("I am modify error hint");
        tvErrorHint.setTextColor(Color.BLUE);
    }

}
