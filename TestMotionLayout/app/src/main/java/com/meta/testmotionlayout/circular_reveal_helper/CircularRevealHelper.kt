package com.meta.testmotionlayout.circular_reveal_helper

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewAnimationUtils
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout

class CircularRevealHelper : ConstraintHelper {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor (context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun updatePostLayout(container: ConstraintLayout?) {
        super.updatePostLayout(container)
        container ?: return
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }
        for (view in getViews(container)) {
            val animator = ViewAnimationUtils.createCircularReveal(
                view,
                view.width / 2,
                view.height / 2,
                0f,
                Math.hypot((view.height / 2).toDouble(), (view.width / 2).toDouble()).toFloat()
            )
            animator.duration = 3000
            animator.start()
        }
    }
}