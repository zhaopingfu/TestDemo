package com.meta.testmotionlayout

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.meta.testmotionlayout.databinding.LayoutConstraintsetStart2Binding

class ConstraintSet2Activity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: LayoutConstraintsetStart2Binding
    private var toggle = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutConstraintsetStart2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        bindData()
    }

    private fun bindData() {
        binding.imageFilmCover.setOnClickListener(this)
        binding.ratingFilmRating.rating = 4.5f
        binding.textFilmTitle.text = getString(R.string.film_title)
        binding.textFilmDescription.text = getString(R.string.film_description)
    }

    override fun onClick(v: View?) {
        v ?: return

        TransitionManager.beginDelayedTransition(binding.root2)
        val constraintSet = ConstraintSet().apply {
            if (toggle) {
                clone(this@ConstraintSet2Activity, R.layout.layout_constraintset_end2)
            } else {
                clone(this@ConstraintSet2Activity, R.layout.layout_constraintset_start2)
            }
        }
        constraintSet.applyTo(binding.root2)
        toggle = !toggle
    }
}
