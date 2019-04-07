# LoadKnife

高性能、高拓展地处理加载中，重试，无数据等界面切换。

## 使用方式

### 初始化

```java
LoadKnife.newBuilder()
        // 无构造参数的 Callback 可不用手动添加，内部会懒加载反射实例化，且只会实例化一次。
        .addCallback(new TimeoutCallback())
        // 替换后默认显示视图
        .defaultCallback(LoadingCallback.class)
        // 转换失败时显示的视图
        .errorCallback(ErrorCallback.class)
        // 类型转换器
        .addConvertor(StateConvertor.create())
        // 初始化默认的 LoadKnife 对象，可调用 build() 构建一个新的 LoadKnife。
        .initializeDefault();
```

### Callback 配置

```java
public class AnimateCallback extends EmptyCallback {

    @Override
    public int getLayoutId() {
        return R.layout.callback_animate;
    }
 
    // 不能提取方法的局部变量作为类成员变量，Callback 仅仅转为转接者使用。
    @Override
    public void onAttach(Context context, ViewHelper viewHelper) {
        View animateView = viewHelper.getView(R.id.view_animate);
        Animation animation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setRepeatCount(Integer.MAX_VALUE);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        animateView.startAnimation(animation);
        Toast.makeText(context.getApplicationContext(), "start animation", Toast.LENGTH_SHORT).show();
    }

    // 不能提取方法的局部变量作为类成员变量，Callback 仅仅转为转接者使用。
    @Override
    public void onDetach(Context context, ViewHelper viewHelper) {
        View animateView = viewHelper.getView(R.id.view_animate);
        if (animateView != null) {
            animateView.clearAnimation();
            Toast.makeText(context.getApplicationContext(), "stop animation", Toast.LENGTH_SHORT).show();
        }
    }

}
```

### 动态修改界面

```java
mLoadService = LoadKnife.getDefault().register(this, new OnReloadListener() {
    @Override
    public void onReload(View v) {
        // 重新加载逻辑
    }
}).setCallBack(EmptyCallback.class, new Transform() {
    @Override
    public void modify(Context context, ViewHelper viewHelper) {
        // 修改 view
    }
});

//or
ViewHelper viewHelper = mLoadService.getViewHelper(EmptyCallback.class);
```

### 注册替换视图

#### Activity 中使用

```java
mLoadService = LoadKnife.getDefault().register(this, new OnReloadListener() {
    @Override
    public void onReload(View v) {
        // 重新加载逻辑
    }
});
```

#### View 中使用

```java
mLoadService = LoadKnife.getDefault().register(view, new Callback.OnReloadListener() {
    @Override
    public void onReload(View v) {
        // 重新加载逻辑
    }
});

```

#### Fragment 中使用

```java
public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = View.inflate(getActivity(), R.layout.fragment_a_content, null);
    loadService = loadKnife.register(rootView, new OnReloadListener() {
        @Override
        public void onReload(View v) {
            
        }
    });
    return loadService.getLoadLayout();
}
```

## 说明

- 此框架的 Callback 作为转接者和视图创建数据来源使用，在全局运行期间是单例的。
- Demo 并没有在退出 Activity 或者 fragment 的时候停止 Handler 的延迟 Post，所以在此期间退出会导致内存泄漏，属正常现象。

## 安装

```gradle
implementation 'me.passin:loadknife:0.0.2'
```

## 混淆

```
-keep public class * extends me.passin.loadknife.callback.Callback
```

## 致谢

本框架参考了 [LoadSir](https://github.com/KingJA/LoadSir) 的设计和 Demo。

## License

    Copyright (C) 2019 Passin

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
