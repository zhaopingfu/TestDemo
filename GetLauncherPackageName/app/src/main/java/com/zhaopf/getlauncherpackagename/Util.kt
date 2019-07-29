package com.zhaopf.getlauncherpackagename

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.util.Log

/**
 *
 * @author PingFu.Zhao
 * 2019/7/29
 */

/**
 * 获取手机上所有 app 的桌面应用的包名和类名
 */
fun getAllHomeIntentName(context: Context): List<ResolveInfo>? {
    // 启动页面的 intent 配置
    // AndroidManifest.xml 里面配置了 <category android:name="android.intent.category.HOME"/>
    val intent = Intent(Intent.ACTION_MAIN, null).apply {
        addCategory(Intent.CATEGORY_HOME)
    }
    // 获取手机上所有的桌面应用的包名和类名
    val result = context.packageManager.queryIntentActivities(intent, 0)
    result.forEach { resolveInfo ->
        Log.d(
            "getAllHomeIntentName",
            "---> installed package name: ${resolveInfo.activityInfo.packageName}  class name: ${resolveInfo.activityInfo.name}"
        )
    }
    return result
}

/**
 * 获取手机上所有 app 的启动页面的包名和类名
 */
fun getAllLauncherIntentName(context: Context): List<ResolveInfo> {
    // 启动页面的 intent 配置
    val intent = Intent(Intent.ACTION_MAIN, null).apply {
        addCategory(Intent.CATEGORY_LAUNCHER)
    }
    // 获取手机上所有的启动页面的包名和类名
    val result = context.packageManager.queryIntentActivities(intent, 0)
    result.forEach { resolveInfo ->
        Log.d(
            "getAllLauncherIntentNam",
            "---> installed package name: ${resolveInfo.activityInfo.packageName}  class name: ${resolveInfo.activityInfo.name}"
        )
    }
    return result
}

/**
 * 获取手机 widget 的包名和类名
 */
fun getWidgetName(context: Context): List<AppWidgetProviderInfo> {
    val installedProviders = AppWidgetManager.getInstance(context).installedProviders
    installedProviders.forEach { appWidgetProviderInfo ->
        Log.d(
            "getWidgetName",
            "---> installed package name: ${appWidgetProviderInfo.provider.packageName}  class name: ${appWidgetProviderInfo.provider.className}"
        )
    }
    return installedProviders
}

@SuppressLint("SetTextI18n")
fun check(context: Context, intent: Intent?) {
    val TAG = "check"
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val fromTask = activityManager.getRunningTasks(2)
    fromTask.forEach {
        Log.d(TAG, "---===> task top activity: ${it?.topActivity?.packageName ?: ""}   ${it?.topActivity?.className}")
        Log.d(
            TAG,
            "---===> task base activity: ${it?.baseActivity?.packageName ?: ""}   ${it?.baseActivity?.className}"
        )
    }

    // 通过反射拿到当前栈所有的activity
    val activityList = JavaUtil.getActivitiesByApplication((context as Activity).application)
    activityList.forEach {
        Log.d(TAG, "---<<<> ${it.packageName}  ${it.componentName.className}")
    }
}

/**
 * 判断是否是从桌面应用跳转过来的
 */
fun checkIsFromHomeLauncherApp(context: Context): Boolean {
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val fromTask = activityManager.getRunningTasks(2)

    // 通过反射拿到当前栈所有的activity
    val activityRecordList = JavaUtil.getActivitiesByApplication((context as Activity).application)

    // 获取手机桌面应用的信息
    val allHomeIntentName = getAllHomeIntentName(context)?.map { it.activityInfo?.packageName ?: "" }

    // 如果只有一个，那么可能是桌面来的
    if (activityRecordList?.size ?: 0 <= 1) {
        val pkgName = fromTask?.get(1)?.baseActivity?.packageName ?: ""
        return allHomeIntentName?.contains(pkgName) ?: false
    }
    return false
}