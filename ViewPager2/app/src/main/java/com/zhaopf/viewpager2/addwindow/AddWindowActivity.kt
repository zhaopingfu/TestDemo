package com.zhaopf.viewpager2.addwindow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.zhaopf.viewpager2.R
import kotlinx.android.synthetic.main.activity_add_window.*

class AddWindowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_window)

        val view = LayoutInflater.from(this)
            .inflate(R.layout.layout_add_view, null, false) as ConstraintLayout
        val btn = view.findViewById<Button>(R.id.btn)
        val decorView = window.decorView as FrameLayout
        decorView.addView(
            view,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        btn.setOnTouchListener(View.OnTouchListener { v, event ->
            ConstraintSet().apply {
                clone(view)
                setHorizontalBias(v.id, (event.rawX + v.width / 2) / view.width)
                setVerticalBias(v.id, event.rawY / view.height)
                applyTo(view)
            }
            return@OnTouchListener true
        })
    }
}