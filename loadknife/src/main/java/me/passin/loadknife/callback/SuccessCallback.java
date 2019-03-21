package me.passin.loadknife.callback;

import android.content.Context;
import me.passin.loadknife.core.ViewHelper;

/**
 * @author: zbb 33775
 * @date: 2019/3/20 22:27
 * @desc:
 */
public class SuccessCallback implements Callback{

    @Override
    public int getLayoutId() {
        return 0;
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
        return true;
    }
}
