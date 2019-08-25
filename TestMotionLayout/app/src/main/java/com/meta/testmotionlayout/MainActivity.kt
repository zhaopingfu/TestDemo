package com.meta.testmotionlayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.meta.testmotionlayout.circular_reveal_helper.CircularRevealActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_jump_group_activity.setOnClickListener {
            startActivity(Intent(it.context, GroupActivity::class.java))
        }

        btn_jump_layer_activity.setOnClickListener {
            startActivity(Intent(it.context, LayerActivity::class.java))
        }

        btn_jump_barrier_activity.setOnClickListener {
            startActivity(Intent(it.context, BarrierActivity::class.java))
        }

        btn_jump_circular_reveal_activity.setOnClickListener {
            startActivity(Intent(it.context, CircularRevealActivity::class.java))
        }
    }
}