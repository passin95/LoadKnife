# LoadKnife

高性能、高拓展地处理加载中，重试，无数据等界面切换。并支持以下特性：

1. 懒加载每一个需要切换的页面，内部代码没有使用对象序列化，以减少内存的使用，在保证拓展性的同时尽可能做到最佳性能。
2. 支持设置类型适配器，入参可传入任何对象转化成最终的 Callback 对象。
3. 支持替换 ConstraintLayout 或 RelativeLayout 中的子视图。需要注意的是：**新的容器视图会使用子视图的所有 LayoutParams 属性以及 id（因此在替换前需要提前通过 findViewById 提取子视图对象），并且子视图放弃了所有 LayoutParams 属性**。
4. 可灵活配置 App 级、Module 级、View 级的 LoadKnife 对象，方便统一维护，无缝对接模块化设计。

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
public class AnimateCallback extends Callback {

     @Override
     public int getLayoutId() {
         return R.layout.callback_animate;
     }

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
    public void onReload(Callback callback, View v) {
        // 重新加载逻辑
    }
})
// 修改 view
ViewHelper viewHelper = mLoadService.getViewHelper(EmptyCallback.class);
```

### 注册替换视图

#### Activity 中使用

```java
mLoadService = LoadKnife.getDefault().register(this, new OnReloadListener() {
    @Override
    public void onReload(Callback callback, View v) {
        // 重新加载逻辑
    }
});
```

#### View 中使用

```java
mLoadService = LoadKnife.getDefault().register(view, new Callback.OnReloadListener() {
    @Override
    public void onReload(Callback callback, View v) {
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
        public void onReload(Callback callback, View v) {
            
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
// 基于 Android X。
implementation 'me.passin:loadknife:1.5.0'
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
