package com.zhaopf.testbasequickadapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 *
 * @author PingFu.Zhao
 * 2019/7/29
 */
class MMultipleAdapter(data: MutableList<DataItemBean>) :
    BaseMultiItemQuickAdapter<DataItemBean, BaseViewHolder>(data) {

    init {
        addItemType(0x10, R.layout.item_rv_big_pic)
        addItemType(0x20, R.layout.item_rv_small_pic)
    }

    override fun convert(helper: BaseViewHolder, item: DataItemBean) {
        when (helper.itemViewType) {
            0x10 -> {
                helper.setImageResource(R.id.iv_big_pic, R.mipmap.ic_launcher)
            }
            0x20 -> {
                helper.setImageResource(R.id.iv_small_pic, R.mipmap.ic_launcher)
            }
        }
    }
}