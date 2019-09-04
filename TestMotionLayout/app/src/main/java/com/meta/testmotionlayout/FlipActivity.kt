package com.meta.testmotionlayout

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import kotlinx.android.synthetic.main.activity_flip.*

class FlipActivity : AppCompatActivity() {

    private val TAG = "FlipActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flip)

        root.setTransitionListener(object : TransitionAdapter() {
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