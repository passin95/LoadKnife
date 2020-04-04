package me.passin.loadknife.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.List;
import me.passin.loadknife.callback.Callback;
import static me.passin.loadknife.utils.Preconditions.checkNotNull;

/**
 * @author: zbb 33775
 * @date: 2019/3/19 15:17
 * @desc: 可自行选择在外部定义一个容器去管理多个 LoadKnife。
 */
public class LoadKnife {

    private static volatile LoadKnife mLoadKnife;

    private final List<Convertor> mConvertors;
    /**
     * 内部抛出可控异常时展示的页面，例如 Convertor 转换失败。
     */
    final Class<? extends Callback> mErrorCallback;
    /**
     * 调用 register() 后默认页面。
     */
    private final Class<? extends Callback> mDefaultCallback;
    /**
     * 视图切换容器视图是否能够使子 View 的 Id，也是兼容约束或相对布局的关键。
     */
    final boolean mIsEnableUseChildViewId;

    private LoadKnife() {
        this(new Builder());
    }

    private LoadKnife(Builder builder) {
        this.mConvertors = unmodifiableList(builder.mConvertors);
        this.mErrorCallback = builder.mErrorCallback;
        this.mDefaultCallback = builder.mDefaultCallback;
        this.mIsEnableUseChildViewId = builder.mIsEnableUseChildViewId;
    }

    public static LoadKnife getDefault() {
        if (mLoadKnife == null) {
            synchronized (LoadKnife.class) {
                if (mLoadKnife == null) {
                    mLoadKnife = new LoadKnife();
                }
            }
        }
        return mLoadKnife;
    }

    /**
     * 主要适用于构造函数含有参数的 callback，多次添加会覆盖。
     * 无参 Callback 可选择不添加，内部会通过懒加载反射初始化。
     */
    public static void addCallback(@NonNull Callback... callback) {
        checkNotNull(callback, "callback == null");
        LoadLayout.addCallback(callback);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public LoadService register(@NonNull Object target) {
        return register(target, null);
    }

    public LoadService register(@NonNull Object target, @Nullable Callback.OnReloadListener onReloadListener) {
        return register(target, onReloadListener, mDefaultCallback);
    }

    public LoadService register(@NonNull Object target, @Nullable Callback.OnReloadListener onReloadListener,
            @Nullable Class<? extends Callback> defaultCallback) {
        return new LoadService(this, target, defaultCallback, onReloadListener);
    }

    @SuppressWarnings("unchecked")
    public Class<? extends Callback> callbackConverter(Object o) {
        for (Convertor convertor : mConvertors) {
            try {
                Class<? extends Callback> callBack = convertor.convert(o);
                if (callBack != null) {
                    return callBack;
                }
            } catch (Exception e) {
                // ignore
            }
        }
        return null;
    }

    public static class Builder {

        private List<Convertor> mConvertors = new ArrayList<>(5);
        private Class<? extends Callback> mDefaultCallback;
        private Class<? extends Callback> mErrorCallback;
        private boolean mIsEnableUseChildViewId = true;

        public Builder addConvertor(@NonNull Convertor convertor) {
            checkNotNull(convertor, "convertor == null");
            mConvertors.add(convertor);
            return this;
        }

        public Builder defaultCallback(@NonNull Class<? extends Callback> defaultCallback) {
            checkNotNull(defaultCallback, "defaultCallback == null");
            mDefaultCallback = defaultCallback;
            return this;
        }

        /**
         * 内部抛出可控异常显示的界面，不设置则默认不处理。
         */
        public Builder errorCallback(@NonNull Class<? extends Callback> errorCallback) {
            checkNotNull(errorCallback, "errorCallback == null");
            mErrorCallback = errorCallback;
            return this;
        }

        public Builder isEnableUseChildViewId(boolean isEnable) {
            mIsEnableUseChildViewId = isEnable;
            return this;
        }

        public void initializeDefault() {
            if (mLoadKnife != null) {
                throw new IllegalArgumentException("already init default LoadKnife");
            } else {
                mLoadKnife = new LoadKnife(this);
            }
        }

        public LoadKnife build() {
            return new LoadKnife(this);
        }

    }

}
