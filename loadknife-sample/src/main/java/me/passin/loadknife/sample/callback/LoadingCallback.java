package me.passin.loadknife.sample.callback;

import android.view.View;
import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.sample.R;

/**
 * @author: zbb 33775
 * @date: 2019/3/21 11:18
 * @desc:
 */
public class LoadingCallback extends Callback {

    @Override
    public int getLayoutId() {
        return R.layout.callback_loading;
    }

    @Override
    public boolean onReloadEvent(View view) {
        return true;
    }

}
