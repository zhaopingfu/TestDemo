package com.meta.testmotionlayout

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.layout_constraintset_start2.*

class ConstraintSet2Activity : AppCompatActivity(), View.OnClickListener {

    private var toggle = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_constraintset_start2)
        bindData()
    }

    private fun bindData() {
        image_film_cover.setOnClickListener(this)
        rating_film_rating.rating = 4.5f
        text_film_title.text = getString(R.string.film_title)
        text_film_description.text = getString(R.string.film_description)
    }

    override fun onClick(v: View?) {
        v ?: return

        TransitionManager.beginDelayedTransition(root2)
        val constraintSet = ConstraintSet().apply {
            if (toggle) {
                clone(this@ConstraintSet2Activity, R.layout.layout_constraintset_end2)
            } else {
                clone(this@ConstraintSet2Activity, R.layout.layout_constraintset_start2)
            }
        }
        constraintSet.applyTo(root2)
        toggle = !toggle
    }
}