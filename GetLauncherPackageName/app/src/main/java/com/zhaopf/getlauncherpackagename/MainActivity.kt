package com.zhaopf.getlauncherpackagename

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_jump_to_second.setOnClickListener {
            startActivity(Intent(this@MainActivity, SecondActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        check(this, intent)
    }
}