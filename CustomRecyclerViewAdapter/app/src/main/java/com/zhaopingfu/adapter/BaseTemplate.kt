package com.zhaopingfu.adapter

/**
 *
 * @author zhaopingfu
 * @date 2021-03-08 15:52
 */
class ATemplate : BaseTemplate<ABean>() {

    override fun convert(holder: MViewHolder, position: Int, bean: ABean) {
    }

    override fun getItemViewId(): Int = R.layout.layout_a_template
}

class BTemplate : BaseTemplate<BBean>() {

    override fun convert(holder: MViewHolder, position: Int, bean: BBean) {
    }

    override fun getItemViewId(): Int = R.layout.layout_b_template
}