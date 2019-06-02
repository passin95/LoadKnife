package me.passin.loadknife.sample.callback;

import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.sample.R;

public class LottieLoadingCallback extends Callback {

    @Override
    public int getLayoutId() {
        return R.layout.callback_lottie_loading;
    }
}
