package me.passin.loadknife.core;

import me.passin.loadknife.callback.Callback;

/**
 * @author: zbb 33775
 * @date: 2019/3/19 15:26
 * @desc: 将一些类型转换为 CallBack
 */
public interface Convertor<T> {

    Class<? extends Callback> convert(T value) throws Exception;

}
