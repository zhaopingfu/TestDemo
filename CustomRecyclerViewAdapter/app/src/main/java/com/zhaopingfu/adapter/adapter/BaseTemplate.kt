package com.zhaopingfu.adapter

/**
 *
 * @author zhaopingfu
 * @date 2021-03-08 15:52
 */
abstract class BaseTemplate<out T : BaseBean> {
    abstract fun convert(position: Int, bean: @UnsafeVariance T)

    abstract fun getItemViewId(): Int
}

class ATemplate : BaseTemplate<ABean>() {
    override fun convert(position: Int, bean: ABean) {
    }

    override fun getItemViewId(): Int = 0
}

class BTemplate : BaseTemplate<BBean>() {
    override fun convert(position: Int, bean: BBean) {
    }

    override fun getItemViewId(): Int = 1
}