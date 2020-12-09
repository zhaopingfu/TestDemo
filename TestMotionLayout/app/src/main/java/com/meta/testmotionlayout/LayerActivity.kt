package com.meta.testmotionlayout

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.meta.testmotionlayout.databinding.ActivityLayerBinding

class LayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn8.setOnClickListener {
            val anim = ValueAnimator.ofFloat(0f, 360f)
            anim.duration = 300
            anim.addUpdateListener { animation ->
                val angle = animation.animatedValue as Float
                binding.layer.rotation = angle
                binding.layer.scaleX = 1 + (180 - Math.abs(angle - 180)) / 20f
                binding.layer.scaleY = 1 + (180 - Math.abs(angle - 180)) / 20f

                val shiftX = 500 * Math.sin(Math.toRadians((angle * 5).toDouble())).toFloat()
                val shiftY = 500 * Math.sin(Math.toRadians((angle * 7).toDouble())).toFloat()
                binding.layer.translationX = shiftX
                binding.layer.translationY = shiftY
            }
            anim.duration = 4000
            anim.start()
        }
    }
}
