package com.zhaopingfu.customview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntDef
import kotlin.math.max

/**
 *
 * @author zhaopingfu
 * @date 2021-03-01 09:54
 */
class TestLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private var mTotalLength = 0

    @OrientationMode
    private var mOrientation = OrientationMode.VERTICAL

    private var mGravity = Gravity.START or Gravity.TOP

    init {
        attrs?.apply {
            val a = context.obtainStyledAttributes(this, R.styleable.TestLinearLayout)
            mOrientation =
                a.getInt(R.styleable.TestLinearLayout_android_orientation, OrientationMode.VERTICAL)
            mGravity =
                a.getInt(R.styleable.TestLinearLayout_android_gravity, Gravity.START or Gravity.TOP)
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mTotalLength = 0

        if (mOrientation == OrientationMode.VERTICAL) {
            var maxWidth = 0
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if (child == null || child.visibility == View.GONE) {
                    continue
                }
                val lp = child.layoutParams as LinearLayoutParams
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, mTotalLength)
                mTotalLength += child.measuredHeight + lp.topMargin + lp.bottomMargin
                maxWidth = max(maxWidth, child.measuredWidth + lp.leftMargin + lp.rightMargin)
            }
            mTotalLength += paddingTop + paddingBottom
            maxWidth += paddingLeft + paddingRight

            mTotalLength = max(mTotalLength, suggestedMinimumHeight)
            maxWidth = max(maxWidth, suggestedMinimumWidth)

            setMeasuredDimension(
                resolveSize(maxWidth, widthMeasureSpec),
                resolveSize(mTotalLength, heightMeasureSpec)
            )
        } else if (mOrientation == OrientationMode.HORIZONTAL) {
            var maxHeight = 0
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if (child == null || child.visibility == View.GONE) {
                    continue
                }
                val lp = child.layoutParams as LinearLayoutParams
                measureChildWithMargins(child, widthMeasureSpec, mTotalLength, heightMeasureSpec, 0)
                mTotalLength += child.measuredWidth + lp.leftMargin + lp.rightMargin
                maxHeight = max(maxHeight, child.measuredHeight + lp.topMargin + lp.bottomMargin)
            }
            mTotalLength += paddingLeft + paddingRight
            maxHeight += paddingTop + paddingBottom

            mTotalLength = max(mTotalLength, suggestedMinimumWidth)
            maxHeight = max(maxHeight, suggestedMinimumHeight)

            setMeasuredDimension(
                resolveSize(mTotalLength, widthMeasureSpec),
                resolveSize(maxHeight, heightMeasureSpec)
            )
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (mOrientation == OrientationMode.VERTICAL) {
            layoutVertical(l, t, r, b)
        } else {
            layoutHorizontal(l, t, r, b)
        }
    }

    @SuppressLint("RtlHardcoded")
    private fun layoutVertical(left: Int, top: Int, right: Int, bottom: Int) {
        val majorGravity = mGravity and Gravity.VERTICAL_GRAVITY_MASK
        val minorGravity = mGravity and Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK

        var childTop = when (majorGravity and Gravity.VERTICAL_GRAVITY_MASK) {
            Gravity.CENTER_VERTICAL -> (bottom - top) / 2 - mTotalLength / 2 + paddingTop
            Gravity.BOTTOM -> (bottom - top) - mTotalLength + paddingTop
            else -> paddingTop
        }

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child == null || child.visibility == View.GONE) {
                continue
            }
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            val lp = child.layoutParams as LinearLayoutParams
            val gravity = if (lp.gravity < 0) minorGravity else lp.gravity
            val absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection)

            val childLeft = when (absoluteGravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
                Gravity.CENTER_HORIZONTAL -> (right - left - paddingLeft - paddingRight) / 2 -
                        childWidth / 2 + lp.leftMargin + paddingLeft - lp.rightMargin
                Gravity.RIGHT, Gravity.END -> right - left - paddingRight - childWidth - lp.rightMargin
                else -> paddingLeft + lp.leftMargin
            }

            childTop += lp.topMargin
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)
            childTop += childHeight + lp.bottomMargin
        }
    }

    @SuppressLint("RtlHardcoded")
    private fun layoutHorizontal(left: Int, top: Int, right: Int, bottom: Int) {
        val majorGravity = mGravity and Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK
        val minorGravity = mGravity and Gravity.VERTICAL_GRAVITY_MASK

        var childLeft = when (Gravity.getAbsoluteGravity(majorGravity, layoutDirection)) {
            Gravity.CENTER_HORIZONTAL -> (right - left) / 2 - mTotalLength / 2 + paddingLeft
            Gravity.RIGHT, Gravity.END -> right - left - mTotalLength + paddingLeft
            else -> paddingLeft
        }

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child == null || child.visibility == View.GONE) {
                continue
            }
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            val lp = child.layoutParams as LinearLayoutParams

            val gravity = if (lp.gravity < 0) minorGravity else lp.gravity
            val childTop = when (gravity and Gravity.VERTICAL_GRAVITY_MASK) {
                Gravity.TOP -> paddingTop + lp.topMargin
                Gravity.CENTER_VERTICAL -> (bottom - top - paddingTop - paddingBottom) / 2 -
                        childHeight / 2 + lp.topMargin - lp.bottomMargin
                Gravity.BOTTOM -> bottom - top - paddingBottom - childHeight - lp.bottomMargin
                else -> paddingTop
            }

            childLeft += lp.leftMargin
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)
            childLeft += childWidth + lp.rightMargin
        }
    }

    override fun generateDefaultLayoutParams(): LayoutParams? {
        if (mOrientation == OrientationMode.HORIZONTAL) {
            return LinearLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        } else if (mOrientation == OrientationMode.VERTICAL) {
            return LinearLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        }
        return null
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return LinearLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        if (p is LinearLayoutParams) {
            return LinearLayoutParams(p)
        } else if (p is MarginLayoutParams) {
            return LinearLayoutParams(p)
        }
        return LinearLayoutParams(p)
    }

    override fun checkLayoutParams(p: LayoutParams): Boolean {
        return p is LinearLayoutParams
    }
}

class LinearLayoutParams : ViewGroup.MarginLayoutParams {

    private var weight = 0.0f
    var gravity = -1

    constructor(width: Int, height: Int) : super(width, height)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        attrs?.apply {
            val a =
                context.obtainStyledAttributes(attrs, R.styleable.TestLinearLayout_Layout)
            this@LinearLayoutParams.weight =
                a.getFloat(R.styleable.TestLinearLayout_Layout_weight, 0.0f)
            this@LinearLayoutParams.gravity =
                a.getInt(R.styleable.TestLinearLayout_Layout_android_layout_gravity, -1)
            a.recycle()
        }
    }

    constructor(params: ViewGroup.LayoutParams) : super(params) {
        weight = 0.0f
        gravity = -1
    }

    constructor(params: ViewGroup.MarginLayoutParams) : super(params) {
        weight = 0.0f
        gravity = -1
    }

    constructor(params: LinearLayoutParams) : super(params) {
        weight = params.weight
        gravity = params.gravity
    }
}

@IntDef(value = [OrientationMode.HORIZONTAL, OrientationMode.VERTICAL])
@Retention(AnnotationRetention.SOURCE)
annotation class OrientationMode {
    companion object {

        const val HORIZONTAL = 0
        const val VERTICAL = 1
    }
}
