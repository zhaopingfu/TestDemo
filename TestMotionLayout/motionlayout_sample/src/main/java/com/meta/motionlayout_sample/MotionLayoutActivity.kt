package com.meta.motionlayout_sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_motion_layout.*

class MotionLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout)

        rating_film_rating.rating = 4.5f
        text_film_title.text = getString(R.string.film_title)
        text_film_description.text = getString(R.string.film_description)
    }
}