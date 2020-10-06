package me.passin.loadknife.sample.callback;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.sample.R;

/**
 * @author: zbb 33775
 * @date: 2019/3/21 14:33
 * @desc:
 */
public class TimeoutCallback extends Callback {

    @NonNull
    @Override
    public View onCreateView(Context context, @Nullable ViewGroup container) {
        return LayoutInflater.from(context).inflate(R.layout.callback_timeout, container, false);
    }

}
