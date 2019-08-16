package com.meta.optimize_plugin

class OptimizeExtension {

    /**
     * 是否使用 tiny 压缩，如果使用，需要配置tinyKey
     */
    boolean useTinyCompress
    /**
     * tiny压缩的key
     */
    String tinyKey
    /**
     * 是否压缩assets下的图片
     */
    boolean isCompressAssets
}