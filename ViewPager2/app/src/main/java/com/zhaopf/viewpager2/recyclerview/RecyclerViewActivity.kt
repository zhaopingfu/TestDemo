package com.zhaopf.viewpager2.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.LayoutInflaterCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.zhaopf.viewpager2.R
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val listOf = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        rvTest.apply {
            setRecycledViewPool(RecyclerView.RecycledViewPool().apply {
                setMaxRecycledViews(0, 20)
            })
            layoutManager = LinearLayoutManager(context).apply {
                isItemPrefetchEnabled = true
                initialPrefetchItemCount = 5
            }
            adapter = TestRvAdapter(listOf)
        }
        PagerSnapHelper().attachToRecyclerView(rvTest)
    }
}

class TestRvAdapter(val list: List<String>) : RecyclerView.Adapter<TestRvHolder>() {

    private val TAG = "TestRvAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestRvHolder {
        Log.d(TAG, "onCreateViewHolder ")
        return TestRvHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_recyclerview_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TestRvHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder 111 $position")
        holder.tv.text = list[position]
    }

    override fun onBindViewHolder(holder: TestRvHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        Log.d(TAG, "onBindViewHolder 222 $position")
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        Log.d(TAG, "onAttachedToRecyclerView ")
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        Log.d(TAG, "onDetachedFromRecyclerView ")
    }

    override fun onViewAttachedToWindow(holder: TestRvHolder) {
        super.onViewAttachedToWindow(holder)
        Log.d(TAG, "onViewAttachedToWindow ")
    }

    override fun onViewDetachedFromWindow(holder: TestRvHolder) {
        super.onViewDetachedFromWindow(holder)
        Log.d(TAG, "onViewDetachedFromWindow ")
    }
}

class TestRvHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var tv: TextView = itemView.findViewById(R.id.tv)
}