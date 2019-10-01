package com.example.testhotfix

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        FixUtil.fix(this, cacheDir.absolutePath + "/patch.jar")
    }
}