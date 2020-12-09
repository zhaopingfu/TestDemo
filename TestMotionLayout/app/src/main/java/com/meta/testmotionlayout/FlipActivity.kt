package com.meta.testmotionlayout

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import com.meta.testmotionlayout.databinding.ActivityFlipBinding

class FlipActivity : AppCompatActivity() {

    private val TAG = "FlipActivity"
    private lateinit var binding: ActivityFlipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.setTransitionListener(object : TransitionAdapter() {
            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                super.onTransitionChange(motionLayout, startId, endId, progress)
                Log.d(TAG, "onTransitionChange: progress: $progress")
            }
        })
    }
}
