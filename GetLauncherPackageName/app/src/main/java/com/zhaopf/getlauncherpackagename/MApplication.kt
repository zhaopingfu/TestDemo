package com.zhaopf.getlauncherpackagename

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 *
 * @author PingFu.Zhao
 * 2019/7/29
 */
class MApplication : Application() {

    companion object {

        /**
         * 记录当前显示的页面的个数
         */
        var currVisibleActivityCount = 0

        /**
         * 是否是从桌面拉起的
         */
        var isFromDeskop = false
    }

    override fun onCreate() {
        super.onCreate()

        // 监听生命周期，判断是否是从桌面拉起来的
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacksAdapter() {

            override fun onActivityStarted(activity: Activity?) {
                super.onActivityStarted(activity)
                // 如果之前没有页面显示，那么就是从桌面拉起来的
                isFromDeskop = currVisibleActivityCount == 0
                currVisibleActivityCount++
            }

            override fun onActivityStopped(activity: Activity?) {
                super.onActivityStopped(activity)
                currVisibleActivityCount--
            }
        })
    }
}

/**
 * 写的时候继承自这个，只需要重写需要的方法就可以了
 */
open class ActivityLifecycleCallbacksAdapter : Application.ActivityLifecycleCallbacks {

    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    }
}