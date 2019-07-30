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
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                // Log.d(TAG, "---> onActivityCreated")
            }

            override fun onActivityStarted(activity: Activity?) {
                isFromDeskop = currVisibleActivityCount == 0
                // Log.d(TAG, "---> onActivityStarted  ${if (count == 0) "是" else "不是"}从桌面拉起的  count: $count")
                currVisibleActivityCount++
                // Log.d(TAG, "---> onActivityStarted count: $count")
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityStopped(activity: Activity?) {
                currVisibleActivityCount--
                // Log.d(TAG, "---> onActivityStopped count: $count")
            }

            override fun onActivityDestroyed(activity: Activity?) {
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }
        })
    }
}