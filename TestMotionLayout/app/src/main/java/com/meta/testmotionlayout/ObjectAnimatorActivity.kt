package com.meta.testmotionlayout

import android.os.Bundle
import android.transition.TransitionManager
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_object_animator.*

class ObjectAnimatorActivity : AppCompatActivity() {

    private var toggle = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_object_animator)

        view.setOnClickListener { child ->
            TransitionManager.beginDelayedTransition(child.parent as ViewGroup)
            val viewGroup = child.parent as ViewGroup
            if (viewGroup is FrameLayout) {
                val layoutParams = child.layoutParams as FrameLayout.LayoutParams
                if (toggle) {
                    layoutParams.gravity = Gravity.END or Gravity.CENTER_VERTICAL
                } else {
                    layoutParams.gravity = Gravity.START or Gravity.CENTER_VERTICAL
                }
                child.layoutParams = layoutParams
            } else if (viewGroup is ConstraintLayout) {
//                ConstraintSet().apply {
//                    connect(child.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
//                    applyTo(viewGroup)
//                }

//                ConstraintProperties(child)
//                    .connect(ConstraintProperties.END, ConstraintProperties.PARENT_ID, ConstraintProperties.END,0)
//                    .apply()

                // ConstraintSet 和 ConstraintProperties  有bug，暂时只能先用这种方式
                val params = child.layoutParams as ConstraintLayout.LayoutParams
                params.startToStart = -1
                params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                child.layoutParams = params
            }
            toggle = !toggle
        }
    }
}