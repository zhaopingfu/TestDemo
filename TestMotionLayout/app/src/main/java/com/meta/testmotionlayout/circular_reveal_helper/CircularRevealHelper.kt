package com.meta.testmotionlayout.circular_reveal_helper

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.ViewAnimationUtils
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.math.hypot

class CircularRevealHelper : ConstraintHelper {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor (context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun updatePostLayout(container: ConstraintLayout?) {
        super.updatePostLayout(container)
        container ?: return
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
        for (i in 0 until mCount) {
            val view = container.getViewById(referencedIds[i])
            val animator = if (i == 0) {
                ViewAnimationUtils.createCircularReveal(
                    view, 0, 0, 0f,
                    hypot(view.height.toDouble(), view.width.toDouble()).toFloat()
                )
            } else {
                ViewAnimationUtils.createCircularReveal(
                    view,
                    view.width / 2,
                    view.height / 2,
                    0f,
                    hypot(view.height / 2.0, view.width / 2.0).toFloat()
                )
            }
            animator.duration = 1500L
            animator.start()
        }
    }
}