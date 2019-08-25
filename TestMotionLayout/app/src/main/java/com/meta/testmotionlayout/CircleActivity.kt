package com.meta.testmotionlayout

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_circle.*

class CircleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle)

        // earth
        ObjectAnimator.ofFloat(0f, 1f).apply {
            duration = 10_000L
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                val params = earth.layoutParams as ConstraintLayout.LayoutParams
                params.circleAngle = 45 + it.animatedFraction * 360
                moon.requestLayout()
            }
            start()
        }

        // moon
        ObjectAnimator.ofFloat(0f, 1f).apply {
            duration = 2_000L
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                val params = moon.layoutParams as ConstraintLayout.LayoutParams
                params.circleAngle = 270 + it.animatedFraction * 360
                moon.requestLayout()
            }
            start()
        }
    }
}