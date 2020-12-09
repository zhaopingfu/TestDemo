package com.meta.testmotionlayout

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.meta.testmotionlayout.databinding.ActivityGroupBinding

class GroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn4.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.clGroupContainer)
            binding.group.visibility = if (binding.group.visibility == View.VISIBLE) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }
}
