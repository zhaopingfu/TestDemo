package com.zhaopf.getlauncherpackagename

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
    }

    override fun onResume() {
        super.onResume()

        check(this, intent)
    }
}