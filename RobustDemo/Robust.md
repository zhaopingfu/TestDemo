
### `Robust` 美团热修复方案

`Robust` 刚上手感觉比 `Sophix` 麻烦一些，实际用下来确实麻烦一些

优点:

    支持实时修复，不需要重新启动应用，当前类的也可以修复

    新增字段不支持，不过可以通过将字段放在新的类来达到目的

缺点:

    Lamda 表达式(比如 (v -> LogUtil.d("xxx") )) 修改不了

    代码侵入，每个修改的方法上面都要加 `@Modify` 注解，新增的方法要加 `@Add` 注解

    不支持资源的修复

生成补丁的时候失败:

    patch method com.meituan.sample.robusttest.SampleClass.multiple(int) haven't insert code by Robust.Cannot patch this method, method.signature  (I)I

    这表明我们想要打补丁的方法并没有被robust插入代码，Robust对一个方法里面只有字段调用的方法的没有插入代码，简单的来说就是这个方法里面都是字段（局部变量和成员变量）的方法，没有方法调用，new操作等，这类方法被我们归类为简单方法，可以在调用这个方法的地方修复这个问题。


如果加载补丁提示找不到类，那么需要配置混淆规则，不要把配置代码混淆掉

踩了个坑：

    setPatchesInfoImplClassFullName 的参数需要是 robust.xml 中 <patchPackname/>的包名，类名必须是 PatchesInfoImpl
    setPatchesInfoImplClassFullName("com.meta.robustdemo.robust.PatchesInfoImpl")