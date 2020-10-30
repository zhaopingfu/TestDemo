package com.zhaopf.testhilt.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.zhaopf.testhilt.data.User
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author zhaopingfu
 * @date 2020/10/30
 */
@AndroidEntryPoint
class MyTextView : AppCompatTextView {

    @Inject
    lateinit var user: User

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    @SuppressLint("SetTextI18n")
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        text = "name: ${user.name} age: ${user.age}"
    }
}
