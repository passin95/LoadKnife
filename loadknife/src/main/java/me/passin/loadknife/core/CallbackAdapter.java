package me.passin.loadknife.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.passin.loadknife.callback.Callback;

/**
 * @author: zbb 33775
 * @date: 2019/3/19 15:26
 * @desc: 将一些类型转换为 Callback
 */
public interface CallbackAdapter<T> {

    @Nullable
    Class<? extends Callback> adapt(@NonNull T value) throws Exception;

}
