package com.zhaopingfu.adapter

/**
 *
 * @author zhaopingfu
 * @date 2021-03-08 15:51
 */
class ABean : BaseBean() {
    override fun getViewType(): Int = 0
}

class BBean : BaseBean() {
    override fun getViewType(): Int = 1
}
