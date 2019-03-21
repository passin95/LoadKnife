package me.passin.loadknife.sample.callback;

import android.content.Context;
import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.core.ViewHelper;
import me.passin.loadknife.sample.R;

public class CustomCallback implements Callback {

    @Override
    public int getLayoutId() {
        return R.layout.callback_custom;
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
