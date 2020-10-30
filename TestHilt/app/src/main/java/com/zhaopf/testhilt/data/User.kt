package com.zhaopf.testhilt.data

/**
 * @author zhaopingfu
 * @date 2020/10/30
 */
data class User constructor(val name: String, var age: Long)


//data class User constructor(val name: String, var age: Long) {
//    @Inject
//    constructor() : this("zhaopf", System.currentTimeMillis()){
//        Log.d("User", ": new user")
//    }
//}
