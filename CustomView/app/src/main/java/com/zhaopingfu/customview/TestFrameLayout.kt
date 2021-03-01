package com.zhaopingfu.customview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlin.math.max

/**
 *
 * @author zhaopingfu
 * @date 2021-02-25 17:36
 */
class TestFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_CHILD_GRAVITY = Gravity.START or Gravity.TOP
    }

    private val matchParentChildren by lazy { ArrayList<View>(1) }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // FrameLayout 本身不是 MatchParent 或者 具体值
        // 也就是说 FrameLayout本身是 wrap Content
        val measureMatchParentChildren =
            MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY
                    || MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY
        matchParentChildren.clear()

        var maxWidth = 0
        var maxHeight = 0
        var childState = 0

        var count = childCount

        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                measureChildWithMargins(
                    child, widthMeasureSpec, 0,
                    heightMeasureSpec, 0
                )

                val lp = child.layoutParams as FrameLayoutParams
                maxWidth =
                    max(maxWidth, child.measuredWidth + lp.leftMargin + lp.rightMargin)
                maxHeight =
                    max(maxHeight, child.measuredHeight + lp.topMargin + lp.bottomMargin)
                childState = combineMeasuredStates(childState, child.measuredState)

                if (measureMatchParentChildren) {
                    if (lp.width == LayoutParams.MATCH_PARENT
                        || lp.height == LayoutParams.MATCH_PARENT
                    ) {
                        matchParentChildren.add(child)
                    }
                }
            }
        }

        maxWidth += paddingLeft + paddingRight
        maxHeight += paddingTop + paddingBottom

        maxWidth = max(maxWidth, suggestedMinimumWidth)
        maxHeight = max(maxHeight, suggestedMinimumHeight)

        setMeasuredDimension(
            resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
            resolveSizeAndState(
                maxHeight,
                heightMeasureSpec,
                childState.shl(MEASURED_HEIGHT_STATE_SHIFT)
            )
        )

        count = matchParentChildren.size
        for (i in 0 until count) {
            val child = getChildAt(i)
            val lp = child.layoutParams as FrameLayoutParams

            val childWidthMeasureSpec = if (lp.width == LayoutParams.MATCH_PARENT) {
                MeasureSpec.makeMeasureSpec(
                    max(
                        0,
                        measuredWidth - paddingLeft - paddingRight - lp.leftMargin - lp.rightMargin
                    ), MeasureSpec.EXACTLY
                )
            } else {
                getChildMeasureSpec(
                    widthMeasureSpec,
                    paddingLeft + paddingRight + lp.leftMargin + lp.rightMargin,
                    lp.width
                )
            }

            val childHeightMeasureSpec = if (lp.height == LayoutParams.MATCH_PARENT) {
                MeasureSpec.makeMeasureSpec(
                    max(
                        0,
                        measuredHeight - paddingTop - paddingBottom - lp.topMargin - lp.bottomMargin
                    ), MeasureSpec.EXACTLY
                )
            } else {
                getChildMeasureSpec(
                    heightMeasureSpec,
                    paddingTop + paddingBottom + lp.topMargin + lp.bottomMargin,
                    lp.height
                )
            }

            child.measure(childWidthMeasureSpec, childHeightMeasureSpec)
        }
    }

    @SuppressLint("RtlHardcoded")
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val paddingLeft = paddingLeft
        val paddingRight = r - l - paddingRight

        val parentTop = paddingTop
        val parentBottom = b - t - paddingBottom

        val count = childCount

        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                val lp = child.layoutParams as FrameLayoutParams

                val width = child.measuredWidth
                val height = child.measuredHeight

                val gravity = if (lp.gravity == -1) DEFAULT_CHILD_GRAVITY else lp.gravity

                val layoutDirection = layoutDirection
                val absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection)
                val verticalGravity = gravity and Gravity.VERTICAL_GRAVITY_MASK

                val childLeft = when (absoluteGravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
                    Gravity.CENTER_HORIZONTAL -> paddingLeft + (paddingRight - paddingLeft) / 2 -
                            width / 2 + lp.leftMargin - lp.rightMargin
                    Gravity.END, Gravity.RIGHT -> paddingRight - width - lp.rightMargin
                    else -> paddingLeft + lp.leftMargin
                }
                val childTop = when (verticalGravity and Gravity.VERTICAL_GRAVITY_MASK) {
                    Gravity.CENTER_VERTICAL -> parentTop + (parentBottom - parentTop) / 2 -
                            height / 2 + lp.topMargin - lp.bottomMargin
                    Gravity.BOTTOM -> parentBottom - height - lp.bottomMargin
                    else -> parentTop + lp.topMargin
                }

                child.layout(
                    childLeft,
                    childTop,
                    childLeft + width,
                    childTop + height
                )
            }
        }
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return FrameLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    override fun checkLayoutParams(p: LayoutParams): Boolean {
        return p is FrameLayoutParams
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return FrameLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        if (p is FrameLayoutParams) {
            return FrameLayoutParams(p)
        } else if (p is MarginLayoutParams) {
            return FrameLayout.LayoutParams(p)
        }

        return FrameLayoutParams(p)
    }
}

class FrameLayoutParams : ViewGroup.MarginLayoutParams {

    companion object {
        const val UNSPECIFIED_GRAVITY = -1
    }

    var gravity: Int = UNSPECIFIED_GRAVITY

    constructor(width: Int, height: Int) : super(width, height)

    constructor(p: ViewGroup.LayoutParams) : super(p)

    constructor(p: FrameLayoutParams) : super(p) {
        this.gravity = p.gravity
    }

    constructor(c: Context, attrs: AttributeSet?) : super(c, attrs) {
        val a = c.obtainStyledAttributes(attrs, R.styleable.TestFrameLayout_Layout)
        gravity = a.getInt(
            R.styleable.TestFrameLayout_Layout_android_layout_gravity,
            UNSPECIFIED_GRAVITY
        )
        a.recycle()
    }
}
