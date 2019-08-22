package com.meta.testmotionlayout

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.meta.testmotionlayout.R
import kotlinx.android.synthetic.main.activity_layer.*

class LayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layer)

        btn8.setOnClickListener {
            val anim = ValueAnimator.ofFloat(0f, 360f)
            anim.duration = 300
            anim.addUpdateListener { animation ->
                val angle = animation.animatedValue as Float
                layer.rotation = angle
                layer.scaleX = 1 + (180 - Math.abs(angle - 180)) / 20f
                layer.scaleY = 1 + (180 - Math.abs(angle - 180)) / 20f

                val shift_x = 500 * Math.sin(Math.toRadians((angle * 5).toDouble())).toFloat()
                val shift_y = 500 * Math.sin(Math.toRadians((angle * 7).toDouble())).toFloat()
                layer.translationX = shift_x
                layer.translationY = shift_y
            }
            anim.duration = 4000
            anim.start()
        }
    }
}