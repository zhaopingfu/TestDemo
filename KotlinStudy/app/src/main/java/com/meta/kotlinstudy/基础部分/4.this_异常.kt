package com.meta.kotlinstudy.基础部分

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meta.kotlinstudy.MainActivity

class MyActivity : Activity() {

    private val mRecyclerView by lazy { RecyclerView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3).apply {
                this@apply.spanCount

                orientation = RecyclerView.HORIZONTAL

                this@MyActivity.startActivity(Intent(context, MainActivity::class.java))
            }
        }
    }
}