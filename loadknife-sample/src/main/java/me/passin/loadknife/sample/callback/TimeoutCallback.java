package me.passin.loadknife.sample.callback;

import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.sample.R;

/**
 * @author: zbb 33775
 * @date: 2019/3/21 14:33
 * @desc:
 */
public class TimeoutCallback extends Callback {

    @Override
    public int getLayoutId() {
        return R.layout.callback_timeout;
    }
}
