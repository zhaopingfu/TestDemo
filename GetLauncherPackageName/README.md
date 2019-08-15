* 获取手机中所有的包名

* 获取手机中所有的 `launcher` 的包名

* 获取页面的跳转来源，是从哪里跳转过来的(这个现在还没办法获取到。。)

* 判断是否是从桌面拉起来的
    
    - 目前发现 `registerActivityLifecycleCallbacks` 方式还可以，但不知道有没有更好的方式
    
    - `registerActivityLifecycleCallbacks` 里面的生命周期方法会优先执行, 而 `activity` 里面的生命周期方法会在后面执行
    
    - 用变量 `count` 记录当前显示的页面个数, `onStart() + 1` , `onStop() - 1` ,这样当为 0 的时候就表示是从桌面拉起的

### 最后的解决办法

通过 `activity` 的 `getReferrer()` 方法能够获取到是从那个包跳转过来的, 但是只有 `API 22` 才支持