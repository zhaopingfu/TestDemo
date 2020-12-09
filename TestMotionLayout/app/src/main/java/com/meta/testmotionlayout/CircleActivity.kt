package com.meta.testmotionlayout

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.meta.testmotionlayout.databinding.ActivityCircleBinding

class CircleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCircleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCircleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // earth
        ObjectAnimator.ofFloat(0f, 1f).apply {
            duration = 10_000L
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                val params = binding.earth.layoutParams as ConstraintLayout.LayoutParams
                params.circleAngle = 45f + it.animatedFraction * 360f
                binding.moon.requestLayout()
            }
            start()
        }

        // moon
        ObjectAnimator.ofFloat(0f, 1f).apply {
            duration = 2_000L
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                val params = binding.moon.layoutParams as ConstraintLayout.LayoutParams
                params.circleAngle = 270f + it.animatedFraction * 360f
                binding.moon.requestLayout()
            }
            start()
        }
    }
}
