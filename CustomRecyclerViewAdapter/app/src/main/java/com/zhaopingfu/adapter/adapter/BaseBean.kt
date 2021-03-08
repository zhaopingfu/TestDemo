package com.zhaopingfu.adapter

import java.io.Serializable

/**
 *
 * @author zhaopingfu
 * @date 2021-03-08 15:51
 */
abstract class BaseBean : Serializable {
    abstract fun getViewType(): Int
}

class ABean : BaseBean() {
    override fun getViewType(): Int = 0
}

class BBean : BaseBean() {
    override fun getViewType(): Int = 1
}
