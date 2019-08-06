package com.meta.robustdemo;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.meituan.robust.PatchExecutor;
import com.meta.robustdemo.robust.PatchManipulateImp;
import com.meta.robustdemo.robust.PermissionUtils;
import com.meta.robustdemo.robust.RobustCallBackSample;

public class AppApplication extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 加载补丁
        if (isGrantSDCardReadPermission()) {
            runRobust();
        }
    }

    private boolean isGrantSDCardReadPermission() {
        return PermissionUtils.isGrantSDCardReadPermission(this);
    }

    private void runRobust() {
        new PatchExecutor(getApplicationContext(), new PatchManipulateImp(), new RobustCallBackSample()).start();
    }
}