package com.zhaopf.getlauncherpackagename.activitys

import android.content.Intent
import android.os.Bundle
import com.zhaopf.getlauncherpackagename.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_jump_to_second.setOnClickListener {
            startActivity(Intent(this@MainActivity, SecondActivity::class.java))
        }
        // 本地修改了1
    }
}