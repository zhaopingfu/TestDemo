package com.example.testhotfix;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.example.testhotfix.fixmethod.FixUtil;

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        FixUtil.fix(this, getCacheDir().getAbsolutePath() + "/patch.jar");
    }
}