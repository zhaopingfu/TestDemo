package com.meta.motionlayout_sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_youtube.setOnClickListener {
            startActivity(Intent(this@MainActivity, YoutubeActivity::class.java))
        }

        btn_motion_layout.setOnClickListener {
            startActivity(Intent(this@MainActivity, MotionLayoutActivity::class.java))
        }
    }
}