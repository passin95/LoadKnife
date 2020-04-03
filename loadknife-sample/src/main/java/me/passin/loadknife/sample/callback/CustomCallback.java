package me.passin.loadknife.sample.callback;

import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.sample.R;

public class CustomCallback extends Callback {

    @Override
    public int getLayoutId() {
        return R.layout.callback_custom;
    }

}
