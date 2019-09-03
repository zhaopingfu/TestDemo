package com.meta.testmotionlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_test_motion_layout.*

class TestMotionLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_motion_layout)

        rating_film_rating.rating = 4.5f
        text_film_title.text = getString(R.string.film_title)
        text_film_description.text = getString(R.string.film_description)
    }
}