
### `Robust` 美团热修复方案

`Robust` 刚上手感觉比 `Sophix` 麻烦一些，实际用下来确实麻烦一些

优点:

    支持实时修复，不需要重新启动应用，当前类的也可以修复

    新增字段不支持，不过可以通过将字段放在新的类来达到目的

    Lamda 表达式(比如 (v -> LogUtil.d("xxx") )) 修改不了，文档上说是在里面添加 RobustModify.modify(); 实测不好使，没效果，可以添加一个新的类来解决

            补丁中添加点击事件的时候不要直接

                setOnClickListener(new View.OnClickListener{
                    // ignore
                })

            这样补丁修复不成功，应该添加一个新的类 XXX，实现 View.OnClickListener 接口，然后setOnClickListener(new XXX())

                @Add
                public static class XXX implements View.OnClickListener{
                    // ignore
                }

缺点:

    代码侵入，每个修改的方法上面都要加 `@Modify` 注解，新增的方法要加 `@Add` 注解

    不支持资源的修复

生成补丁的时候失败:

    patch method com.meituan.sample.robusttest.SampleClass.multiple(int) haven't insert code by Robust.Cannot patch this method, method.signature  (I)I

    这表明我们想要打补丁的方法并没有被robust插入代码，Robust对一个方法里面只有字段调用的方法的没有插入代码，简单的来说就是这个方法里面都是字段（局部变量和成员变量）的方法，没有方法调用，new操作等，这类方法被我们归类为简单方法，可以在调用这个方法的地方修复这个问题。


如果加载补丁提示找不到类，那么需要配置混淆规则，不要把配置代码混淆掉

踩了个坑：

    setPatchesInfoImplClassFullName 的参数需要是 robust.xml 中 <patchPackname/>的包名，类名必须是 PatchesInfoImpl
    setPatchesInfoImplClassFullName("com.meta.robustdemo.robust.PatchesInfoImpl")

又踩了个坑：

    把 patch.jar 往手机里推的时候
    用的 adb push ./patch.jar /storage/emulated/0/robust/
    在 OPPO A57t 上面可以，但是 OPPO R7, 1+7Pro上面都不可以
    一直说找不到文件，最后用
    adb push ./patch.jar /storage/emulated/0/robust/patch.jar


# 总结

### Sophix

    默认不会强制杀死APP，但是有些问题只有冷启动之后才会被修复

        1. 只改不增方法，热启动 当前页面的修复不了，新的页面可以修复

        2. 新增方法，冷启动才能修复

        修复时长：计算code9到code12的时间

            第一次加载补丁在1.5-2s左右，加载完之后就检测不到新的补丁
    
### Robust

    支持实时修复，不需要重新启动应用，当前类的也可以修复

    修复时长：第一次加载补丁在200-300ms左右，之后再加载再25-45ms左右