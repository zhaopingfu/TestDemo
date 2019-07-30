package com.zhaopf.getlauncherpackagename.activitys

import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.zhaopf.getlauncherpackagename.MApplication

/**
 *
 * @author PingFu.Zhao
 * 2019/7/30
 */
open class BaseActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        val tag = javaClass.name
        if (MApplication.isFromDeskop) {
            Log.d(tag, "---> $tag onStart 是从桌面拉起的")
        } else {
            Log.d(tag, "---> $tag onStart 不是从桌面拉起的")
        }
    }

    override fun onResume() {
        super.onResume()
        val tag = javaClass.name
        if (MApplication.isFromDeskop) {
            Log.d(tag, "---> $tag onResume 是从桌面拉起的")
        } else {
            Log.d(tag, "---> $tag onResume 不是从桌面拉起的")
        }
    }
}