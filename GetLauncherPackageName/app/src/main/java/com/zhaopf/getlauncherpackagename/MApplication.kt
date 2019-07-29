package com.zhaopf.getlauncherpackagename

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log

/**
 *
 * @author PingFu.Zhao
 * 2019/7/29
 */
class MApplication : Application() {

    private var count = 0

    private val TAG = "MApplication"

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityStarted(activity: Activity?) {
                count++
                Log.d(TAG, "---> onActivityStarted count: $count")
            }

            override fun onActivityDestroyed(activity: Activity?) {
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
                count--
                Log.d(TAG, "---> onActivityStopped count: $count")
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            }
        })
    }
}