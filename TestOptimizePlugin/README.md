
### 图片压缩

这里没有考虑兼容 `api18` 以下的机型(api18以下带透明度通道的不支持转换为webp)

1、先收集所有 `assets`, `res` 目录下的 `png`, 'jpg' 图片

排除 `.9.png` 和 `启动图标`

2、将图片转换为 `webp`

3、`webp` 转换失败的进行压缩处理

### 使用

先执行 `meta_optimize_plugin` 模块下 `/publishing/publishOptimizerPublicationToMavenLocal` 任务

然后执行 `root` 模块下的 `optimizer/optimizePicture` 任务