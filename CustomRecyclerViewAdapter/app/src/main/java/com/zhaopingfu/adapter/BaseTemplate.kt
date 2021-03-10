package com.zhaopingfu.adapter

import android.graphics.Color
import com.zhaopingfu.adapter.databinding.LayoutATemplateBinding
import com.zhaopingfu.adapter.databinding.LayoutBTemplateBinding

/**
 *
 * @author zhaopingfu
 * @date 2021-03-08 15:52
 */
class ATemplate : BaseTemplate<ABean, LayoutATemplateBinding>() {
    override fun bindData(
        holder: BindingViewHolder<LayoutATemplateBinding>,
        bean: ABean,
        position: Int
    ) {
        holder.binding.root.setBackgroundColor(Color.GRAY)
    }
}

class BTemplate : BaseTemplate<BBean, LayoutBTemplateBinding>() {
    override fun bindData(
        holder: BindingViewHolder<LayoutBTemplateBinding>,
        bean: BBean,
        position: Int
    ) {
        holder.binding.root.setBackgroundColor(Color.DKGRAY)
    }
}
