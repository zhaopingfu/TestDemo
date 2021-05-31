package com.zhaopingfu.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.ScrollView
import kotlin.math.abs

/**
 * https://juejin.cn/post/6844903761060577294
 *
 * @author zhaopingfu
 * @date 2021-03-10 16:37
 */
class SimpleNestedScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr) {

    private var isFirstIntercept = true

    /**
     * 第一次「拦截滑动的判定条件」成立时，先不进行拦截，如果内部没有申请外部不拦截，第二次条件成立时，再进行拦截
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
            isFirstIntercept = true
        }

        val result = super.onInterceptTouchEvent(ev)

        if (result && isFirstIntercept) {
            isFirstIntercept = false
            return false
        }

        return result
    }
}

class InnerNestedScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr) {

    override fun scrollBy(x: Int, y: Int) {
        if ((y > 0 && isScrollToTop()) || (y < 0 && isScrollToBottom())) {
            (parent as View).scrollBy(x, y)
        } else {
            super.scrollBy(x, y)
        }
    }

    private var isNeedRequestDisallowIntercept: Boolean? = null
    private val mTouchSlop by lazy { ViewConfiguration.get(context).scaledTouchSlop }

    /**
     * 在自己无法滑动时，返回false
     */
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (ev.actionMasked == MotionEvent.ACTION_DOWN) isNeedRequestDisallowIntercept = null

        if (ev.actionMasked == MotionEvent.ACTION_MOVE) {
            if (isNeedRequestDisallowIntercept == false) return false

            if (isNeedRequestDisallowIntercept == null) {
                val offsetY = ev.y.toInt() - getInt("mLastMotionY")
                if (abs(offsetY) > mTouchSlop) {
                    if ((offsetY > 0 && isScrollToTop()) || (offsetY < 0 && isScrollToBottom())) {
                        isNeedRequestDisallowIntercept = false
                        return false
                    }
                }
            }
        }
        return super.onTouchEvent(ev)
    }

    private fun getInt(fieldName: String): Int {
        val field = javaClass.superclass.getDeclaredField(fieldName).apply { isAccessible = true }
        return field.getInt(this)
    }

    private fun isScrollToTop(): Boolean = scrollY == 0

    private fun isScrollToBottom(): Boolean {
        return scrollY + height - paddingTop - paddingBottom == getChildAt(0).height
    }
}

class InnerNestedScrollView2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr) {

    private val TAG = "InnerNestedScrollView2"

    override fun scrollBy(x: Int, y: Int) {
        if ((y > 0 && isScrollToTop()) || (y < 0 && isScrollToBottom())) {
            Log.d(TAG, "scrollBy: 1111")
            (parent as View).scrollBy(x, y)
        } else {
            Log.d(TAG, "scrollBy: 222: $y")
            super.scrollBy(x, y)
        }
    }

    private val mTouchSlop by lazy { ViewConfiguration.get(context).scaledTouchSlop }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
            parent?.requestDisallowInterceptTouchEvent(true)
        }
        if (ev.actionMasked == MotionEvent.ACTION_MOVE) {
            val offsetY = ev.y.toInt() - getInt("mLastMotionY")
            if (abs(offsetY) > mTouchSlop) {
                if ((offsetY > 0 && isScrollToTop()) || (offsetY < 0 && isScrollToBottom())) {
                    parent?.requestDisallowInterceptTouchEvent(false)
                    return false
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun getInt(fieldName: String): Int {
        val field = javaClass.superclass.getDeclaredField(fieldName).apply { isAccessible = true }
        return field.getInt(this)
    }

    private fun isScrollToTop(): Boolean = scrollY == 0

    private fun isScrollToBottom(): Boolean {
        return scrollY + height - paddingTop - paddingBottom == getChildAt(0).height
    }
}