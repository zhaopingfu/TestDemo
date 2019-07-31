package com.zhaopf.getlauncherpackagename.activitys

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.zhaopf.getlauncherpackagename.utils.checkIsFromHomeLauncherApp

/**
 * @author PingFu.Zhao
 * 2019/7/30
 */
open class BaseActivity : AppCompatActivity() {

    private val TAG = "BaseActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (checkIsFromHomeLauncherApp(this)) {
            Log.d(TAG, "from desktop")
        } else {
            Log.d(TAG, "not from desktop")
        }
    }
}