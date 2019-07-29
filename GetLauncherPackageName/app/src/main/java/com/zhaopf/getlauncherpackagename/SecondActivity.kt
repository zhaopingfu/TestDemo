package com.zhaopf.getlauncherpackagename

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_second.*

/**
 * 二级页面，用来判断是否是从桌面跳转过来的
 */
class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        btn_show_info.setOnClickListener {
            startActivity(Intent(this@SecondActivity, ThirdActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        check(this, intent)
    }
}