package com.meta.testmotionlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import com.meta.testmotionlayout.databinding.ActivityWithCoordinatorLayoutBinding

class WithCoordinatorLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWithCoordinatorLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            binding.motionLayout.progress =
                -verticalOffset / appBarLayout.totalScrollRange.toFloat()
        })
    }
}
