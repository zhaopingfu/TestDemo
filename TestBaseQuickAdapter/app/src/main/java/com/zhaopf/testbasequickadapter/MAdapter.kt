package com.zhaopf.testbasequickadapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 *
 * @author PingFu.Zhao
 * 2019/7/29
 *
 * 最简单的显示数据
 */
class MAdapter(layoutResId: Int, data: MutableList<DataItemBean>) :
    BaseQuickAdapter<DataItemBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: DataItemBean) {
        helper.setImageResource(R.id.iv_item, R.mipmap.ic_launcher)
            .setText(R.id.btn_item, "${helper.adapterPosition}")
            .addOnClickListener(R.id.btn_item)
    }
}