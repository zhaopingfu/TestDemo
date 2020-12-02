package com.meta.kotlinstudy

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

/**
 *
 *
 * @author zhaopingfu
 * @date 2020/12/2
 */

inline fun <reified T : Any> noOpDelegate(): T {
    val javaClass = T::class.java
    val noOpHandler = InvocationHandler { _, _, _ ->

    }
    return Proxy.newProxyInstance(javaClass.classLoader, arrayOf(javaClass), noOpHandler) as T
}

val lifecycleCallbacks = object : Application.ActivityLifecycleCallbacks by noOpDelegate() {
    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }
}

inline fun <reified T : Activity> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

class  AaActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity<MainActivity>()
    }
}