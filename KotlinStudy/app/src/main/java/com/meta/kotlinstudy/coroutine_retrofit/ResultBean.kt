package com.meta.kotlinstudy.coroutine_retrofit

/**
 *
 * @author zhaopingfu
 * @date 2021-05-31 10:24
 */
data class ResultData<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String,
)

data class BannerItemBean(
    val desc: String?,
    val id: Int = 0,
    val imagePath: String?,
    val isVisible: Int = 0,
    val order: Int = 0,
    val title: String?,
    val type: Int = 0,
    val url: String?,
)