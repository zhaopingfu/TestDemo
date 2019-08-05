package com.meta.sophixdemo;

import android.app.Application;
import android.util.Log;

/**
 * @author PingFu.Zhao
 * 2019/8/5
 * <p>
 * SophixStubApplication 里面有多dex处理了，这里不需要，避免重复弄，出问题
 */
public class AppApplication extends Application {

    private static final String TAG = "AppApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate: ---> " + getApplicationContext());
    }
}