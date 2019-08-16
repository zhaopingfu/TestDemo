
### 图片压缩

这里没有考虑兼容 `api18` 以下的机型(api18以下带透明度通道的不支持转换为webp)

    1、先收集所有 `assets`, `res` 目录下的 `png`, `jpg` 图片

        排除 `.9.png` 和 `启动图标`

    2、将图片转换为 `webp`

    3、`webp` 转换失败的进行压缩处理

        `png` 优先使用 `tiny` 压缩, 如果压缩失败采用 `pngCrush` 压缩

        `jpg` 采用 `guetzli` 压缩

### 使用

    1、添加插件依赖 apply plugin: 'com.meta.optimizer'

        如果找不到插件, 先把插件注释掉, 然后执行 `meta_optimize_plugin` 模块下 `/publishing/publishOptimizerPublicationToMavenLocal` 任务

    2、配置参数

        optimizePicture {
            // 是否使用tiny压缩
            useTinyCompress true
            // 如果使用tiny压缩的话, 需要去官网申请key, 每个月前500张图片免费
            tinyKey rootConfig.tinyKey
            // 是否压缩 assets 下的图片
            isCompressAssets true
        }

    3、执行模块下的 `optimizer/optimizePicture` 任务