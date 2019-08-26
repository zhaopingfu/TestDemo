package com.meta.testmotionlayout.fly_in_helper

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout

class FlyInHelper : ConstraintHelper {

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
        // 中心点的位置
        val centerPointF = PointF((left + right) / 2f, (top + bottom) / 2f)
        ValueAnimator.ofFloat(0F, 1F).apply {
            duration = 1000L
            addUpdateListener {
                getViews(container).forEach { view ->
                    val viewCenterX = (view.left + view.right) / 2f
                    val viewCenterY = (view.top + view.bottom) / 2f

                    val startTranslationX = if (viewCenterX > centerPointF.x) 600F else -600F
                    val startTranslationY = if (viewCenterY > centerPointF.y) 600F else -600F

                    view.translationX = (1f - animatedFraction) * startTranslationX
                    view.translationY = (1f - animatedFraction) * startTranslationY
                }
            }
            start()
        }
    }
}