package com.meta.testmotionlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class ConstraintSetActivity : AppCompatActivity() {

    private var shouldToEnd = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_constraintset_start)
    }

    fun onClick(v: View) {
        val constraintLayout = v as ConstraintLayout
        TransitionManager.beginDelayedTransition(constraintLayout)
        val constraintSet = ConstraintSet().apply {
            if (shouldToEnd) {
                clone(this@ConstraintSetActivity, R.layout.layout_constraintset_end)
            } else {
                clone(this@ConstraintSetActivity, R.layout.layout_constraintset_start)
            }
            shouldToEnd = !shouldToEnd
        }
        constraintSet.applyTo(constraintLayout)
    }
}