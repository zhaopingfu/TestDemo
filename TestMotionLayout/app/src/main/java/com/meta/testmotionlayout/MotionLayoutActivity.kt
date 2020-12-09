package com.meta.testmotionlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.meta.testmotionlayout.databinding.ActivityMotionLayoutBinding

class MotionLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMotionLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ratingFilmRating.rating = 4.5f
        binding.textFilmTitle.text = getString(R.string.film_title)
        binding.textFilmDescription.text = getString(R.string.film_description)
    }
}
