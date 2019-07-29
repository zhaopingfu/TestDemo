package com.zhaopf.testbasequickadapter

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable

/**
 *
 * @author PingFu.Zhao
 * 2019/7/29
 */
data class DataItemBean(
    val avatarUrl: String?,
    val mItemType: Int
) : Serializable, MultiItemEntity {

    override fun getItemType(): Int = mItemType
}