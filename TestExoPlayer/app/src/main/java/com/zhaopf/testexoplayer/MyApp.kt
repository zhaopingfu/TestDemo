package com.zhaopf.testexoplayer

import android.app.Application
import com.danikula.videocache.HttpProxyCacheServer

class MyApp : Application() {

    companion object {

        lateinit var instance: MyApp

        val proxy: HttpProxyCacheServer by lazy {
            HttpProxyCacheServer.Builder(instance)
                .cacheDirectory(instance.getExternalFilesDir("videoCache"))
                .build()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}