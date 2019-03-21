package me.passin.loadknife.sample.callback;

import android.content.Context;
import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.core.ViewHelper;
import me.passin.loadknife.sample.R;

/**
 * @author: zbb 33775
 * @date: 2019/3/19 16:16
 * @desc:
 */
public class EmptyCallback implements Callback {

    @Override
    public int getLayoutId() {
        return R.layout.callback_empty;
    }

    @Override
    public void onViewCreate(Context context, ViewHelper viewHelper) {
    }

    @Override
    public void onAttach(Context context, ViewHelper viewHelper) {
    }

    @Override
    public void onDetach(Context context, ViewHelper viewHelper) {
    }

    @Override
    public boolean successViewVisible() {
        return false;
    }
}
