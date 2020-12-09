package com.meta.testmotionlayout

import android.os.Bundle
import android.transition.Scene
import android.transition.TransitionManager
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.meta.testmotionlayout.databinding.ActivityGoBinding

class GoActivity : AppCompatActivity(), View.OnClickListener {

    private var toggle = true
    private lateinit var binding: ActivityGoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindData()
    }

    private fun bindData() {
        findViewById<ImageView>(R.id.image_film_cover).setOnClickListener(this)
        findViewById<RatingBar>(R.id.rating_film_rating).rating = 4.5f
        findViewById<TextView>(R.id.text_film_title).text = getString(R.string.film_title)
        findViewById<TextView>(R.id.text_film_description).text =
            getString(R.string.film_description)
    }

    override fun onClick(view: View) {
        val startScene = Scene.getSceneForLayout(binding.root, R.layout.layout_go_start, this)
        val endScene = Scene.getSceneForLayout(binding.root, R.layout.layout_go_end, this)
        if (toggle) {
            TransitionManager.go(endScene)
        } else {
            TransitionManager.go(startScene)
        }

        bindData()
        toggle = !toggle
    }
}
