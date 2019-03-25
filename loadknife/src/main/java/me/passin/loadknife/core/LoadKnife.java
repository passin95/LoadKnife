package me.passin.loadknife.core;

import static java.util.Collections.unmodifiableList;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;
import me.passin.loadknife.callback.Callback;

/**
 * @author: zbb 33775
 * @date: 2019/3/19 15:17
 * @desc: 可自行选择在外部定义一个容器去管理多个 LoadKnife。
 */
public class LoadKnife {

    static boolean isDebug = false;
    static volatile LoadKnife LoadKnife;
    final List<Convertor> mConvertors;
    /**
     * 内部抛出可控异常时展示的页面
     */
    final Class<? extends Callback> mErrorCallback;
    /**
     * 调用 register() 后默认页面
     */
    private final Class<? extends Callback> mDefaultCallback;

    public static LoadKnife getDefault() {
        if (LoadKnife == null) {
            synchronized (LoadKnife.class) {
                if (LoadKnife == null) {
                    LoadKnife = new LoadKnife();
                }
            }
        }
        return LoadKnife;
    }

    public static void openDebug() {
        isDebug = true;
    }

    LoadKnife() {
        this(new Builder());
    }

    LoadKnife(Builder builder) {
        this.mConvertors = unmodifiableList(builder.mConvertors);
        this.mErrorCallback = builder.mErrorCallback;
        this.mDefaultCallback = builder.mDefaultCallback;
    }

    public LoadService register(@NonNull Object target) {
        return register(target, null);
    }

    public LoadService register(@NonNull Object target, Callback.OnReloadListener onReloadListener) {
        return register(target, onReloadListener, mDefaultCallback);
    }

    public LoadService register(@NonNull Object target, Callback.OnReloadListener onReloadListener,
            Class<? extends Callback> defaultCallback) {
        return new LoadService(this, target, defaultCallback, onReloadListener);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Class<? extends Callback> callbackConverter(Object o) throws Exception {
        for (Convertor convertor : mConvertors) {
            try {
                Class<? extends Callback> callBack = convertor.convert(o);
                if (callBack != null) {
                    return callBack;
                }
            } catch (Exception e) {
                throw new Exception(String.format("%s convertor %o fail", Convertor.class.getSimpleName(),
                        o.getClass().getSimpleName()));
            }
        }
        return null;
    }

    public static class Builder {

        private List<Convertor> mConvertors = new ArrayList<>(5);
        private Class<? extends Callback> mDefaultCallback;
        private Class<? extends Callback> mErrorCallback;

        public Builder addConvertor(@NonNull Convertor convertor) {
            mConvertors.add(convertor);
            return this;
        }

        public Builder defaultCallback(@NonNull Class<? extends Callback> defaultCallback) {
            mDefaultCallback = defaultCallback;
            return this;
        }

        /**
         * 内部抛出可控异常显示的界面，不设置则默认不处理。
         */
        public Builder errorCallback(@NonNull Class<? extends Callback> errorCallback) {
            mErrorCallback = errorCallback;
            return this;
        }

        /**
         * 主要适用于构造函数含有参数的 callback，多次添加会覆盖。
         * 无参 Callback 可选择不添加，内部会通过懒加载反射初始化。
         */
        public Builder addCallback(Callback callback) {
            LoadLayout.addCallback(callback);
            return this;
        }

        public void initializeDefault() {
            if (LoadKnife != null && isDebug) {
                throw new IllegalArgumentException("already init default LoadKnife");
            } else {
                LoadKnife = new LoadKnife(this);
            }
        }

        public LoadKnife build() {
            return new LoadKnife(this);
        }

    }
}
