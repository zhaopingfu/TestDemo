package com.meta.kotlinstudy.test

/**
 * @author pingfu.zhao
 * @date 2019-09-21 15:20
 */
object BaseSingleton {

}

class LazyLoadSingleton private constructor() {
    companion object {
        /**
         * 这里是线程安全的
         */
        val INSTANCE by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            LazyLoadSingleton()
        }
    }
}

class LazySynchronizedSingleton private constructor() {
    companion object {
        private var INSTANCE: LazySynchronizedSingleton? = null
        @Synchronized
        fun getInstance(): LazySynchronizedSingleton {
            if (null == INSTANCE) {
                INSTANCE = LazySynchronizedSingleton()
            }
            return INSTANCE!!
        }
    }
}