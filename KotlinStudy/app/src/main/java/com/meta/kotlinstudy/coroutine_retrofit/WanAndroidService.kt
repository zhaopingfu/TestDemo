package com.meta.kotlinstudy.coroutine_retrofit

import retrofit2.http.GET

/**
 *
 * @author zhaopingfu
 * @date 2021-05-31 10:18
 */
interface WanAndroidService {

    @GET("banner/json")
    suspend fun listRepos(): ResultData<List<BannerItemBean>>
}
