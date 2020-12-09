package com.meta.testmotionlayout

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.meta.testmotionlayout.databinding.ActivityPlaceHolderBinding

class PlaceHolderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaceHolderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceHolderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.placeholder.postDelayed({
            TransitionManager.beginDelayedTransition(binding.placeholderContainer)
            binding.placeholder.setContentId(binding.favorite.id)
        }, 60L)
    }

    fun onClick(v: View) {
        TransitionManager.beginDelayedTransition(binding.placeholderContainer)
        binding.placeholder.setContentId(v.id)
    }
}
