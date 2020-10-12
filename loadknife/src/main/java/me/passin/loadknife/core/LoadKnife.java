package me.passin.loadknife.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.List;
import java.util.Map;
import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.core.target.ActivityTargetAdapter;
import me.passin.loadknife.core.target.TargetAdapter;
import me.passin.loadknife.core.target.ViewTargetAdapter;
import static me.passin.loadknife.utils.Preconditions.checkNotNull;

/**
 * @author: zbb 33775
 * @date: 2019/3/19 15:17
 * @desc: 灵活配置 App 级、Module 级、View 级的 LoadKnife 对象。
 */
public class LoadKnife {

    private static volatile LoadKnife mLoadKnife;

    @Nullable
    private final List<CallbackAdapter> mCallbackAdapters;
    @NonNull
    private final List<TargetAdapter> mTargetAdapters;
    /**
     * 调用 register() 后默认页面。
     */
    @Nullable
    private final Class<? extends Callback> mDefaultCallback;
    /**
     * callback 容器。
     */
    @NonNull
    final Map<Class<? extends Callback>, Callback> mCallbackMap;
    /**
     * 视图切换容器视图是否能够使子 View 的 Id，也是兼容约束或相对布局的关键。
     */
    private final boolean mIsEnableUseChildViewId;

    private LoadKnife() {
        this(new Builder());
    }

    private LoadKnife(Builder builder) {
        if (builder.mCallbackAdapters != null) {
            this.mCallbackAdapters = unmodifiableList(builder.mCallbackAdapters);
        } else {
            this.mCallbackAdapters = null;
        }
        this.mTargetAdapters = unmodifiableList(builder.mTargetAdapters);
        this.mDefaultCallback = builder.mDefaultCallback;
        this.mIsEnableUseChildViewId = builder.mIsEnableUseChildViewId;
        this.mCallbackMap = builder.callbackMap;
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
    @NonNull
    Class<? extends Callback> callbackAdapter(@NonNull Object o) {
        Class<? extends Callback> callback;
        if (mCallbackAdapters != null) {
            for (CallbackAdapter convertor : mCallbackAdapters) {
                try {
                    callback = convertor.adapt(o);
                    if (callback != null) {
                        return callback;
                    }
                } catch (Exception e) {
                    // ignore
                }
            }
        }
        throw new IllegalArgumentException("Could not locate state callback adapter for " + o.getClass().getCanonicalName());
    }

    @NonNull
    LoadLayout targetAdapter(Object target) {
        for (TargetAdapter targetAdapter : mTargetAdapters) {
            try {
                LoadLayout loadLayout = targetAdapter.adapt(this, target);
                if (loadLayout != null) {
                    return loadLayout;
                }
            } catch (Exception e) {
                // ignore
            }
        }
        throw new IllegalArgumentException("Could not locate target adapter for " + target.getClass().getCanonicalName());
    }

    public boolean isEnableUseChildViewId() {
        return mIsEnableUseChildViewId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private List<CallbackAdapter> mCallbackAdapters;
        private Map<Class<? extends Callback>, Callback> callbackMap = new ArrayMap<>();
        private List<TargetAdapter> mTargetAdapters = new ArrayList<>(5);
        private Class<? extends Callback> mDefaultCallback;
        private boolean mIsEnableUseChildViewId = true;

        {
            mTargetAdapters.add(ActivityTargetAdapter.getInstance());
            mTargetAdapters.add(ViewTargetAdapter.getInstance());
        }

        public Builder addCallbackConvertor(@NonNull CallbackAdapter adapter) {
            checkNotNull(adapter, "convertor == null");
            getCallbackAdapters().add(adapter);
            return this;
        }

        public Builder addTargetConverter(@NonNull TargetAdapter adapter) {
            checkNotNull(adapter, "convertor == null");
            mTargetAdapters.add(adapter);
            return this;
        }

        public Builder defaultCallback(@NonNull Class<? extends Callback> defaultCallback) {
            checkNotNull(defaultCallback, "defaultCallback == null");
            mDefaultCallback = defaultCallback;
            return this;
        }

        /**
         * 主要适用于构造函数含有参数的 callback，多次添加会覆盖。
         * 无参 Callback 可选择不添加，内部会通过懒加载反射初始化。
         */
        public Builder addCallback(@NonNull Callback callback) {
            checkNotNull(callback, "callback == null");
            callbackMap.put(callback.getClass(), callback);
            return this;
        }

        public Builder isEnableUseChildViewId(boolean isEnable) {
            mIsEnableUseChildViewId = isEnable;
            return this;
        }

        private List<CallbackAdapter> getCallbackAdapters() {
            if (mCallbackAdapters == null) {
                mCallbackAdapters = new ArrayList<>(5);
            }
            return mCallbackAdapters;
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
