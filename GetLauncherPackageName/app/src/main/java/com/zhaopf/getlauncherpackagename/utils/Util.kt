package com.zhaopf.getlauncherpackagename.utils

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
 * 获取手机上所有 app 的桌面的包名和类名
 */
fun getAllHomeIntentName(context: Context): List<ResolveInfo>? {
    // 启动页面的 intent 配置
    // AndroidManifest.xml 里面配置了 <category android:name="android.intent.category.HOME"/>
    val intent = Intent(Intent.ACTION_MAIN, null).apply {
        addCategory(Intent.CATEGORY_HOME)
    }
    // 获取手机上所有的桌面应用的包名和类名
    return context.packageManager.queryIntentActivities(intent, 0)
}

/**
 * 判断是否是从桌面应用跳转过来的
 */
fun checkIsFromHomeLauncherApp(activity: Activity): Boolean {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
        val referrer = activity.referrer ?: return false
        return getAllHomeIntentName(activity)
            ?.map { it.activityInfo?.packageName ?: "" }
            ?.contains(referrer.host ?: "")
            ?: false
    }
    return false
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
    val activityList =
        JavaUtil.getActivitiesByApplication((context as Activity).application)
    activityList.forEach {
        Log.d(TAG, "---<<<> ${it.packageName}  ${it.componentName.className}")
    }
}