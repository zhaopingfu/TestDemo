package com.zhaopf.testbasequickadapter

import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 *
 * @author PingFu.Zhao
 * 2019/7/29
 *
 * 侧滑
 */
class MDraggableAdapter(layoutResId: Int, data: MutableList<DataItemBean>?) :
    BaseItemDraggableAdapter<DataItemBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: DataItemBean?) {

    }
}