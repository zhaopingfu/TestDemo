package com.zhaopf.testbasequickadapter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 简单的展示数据
        // testSample()

        // 测试多布局
        testMultipleAdapter()
    }

    private fun testMultipleAdapter() {
        val dataList = mutableListOf<DataItemBean>()
        for (i in 0..50) {
            dataList.add(DataItemBean("", if (i % 5 == 0) 0x10 else 0x20))
        }
        // 经过测试发现，重写adapter里面的 spanSizeLookup 方法不管用，layoutManager里面的好使
        val mAdapter = MMultipleAdapter(dataList)
        val mLayoutManager = GridLayoutManager(this, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = mAdapter.getItemViewType(position)
                    return if (viewType == 0x10) spanCount else 1
                }
            }
        }
        rv.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
        }
    }

    /**
     * 简单的展示数据
     */
    private fun testSample() {
        val dataList = mutableListOf<DataItemBean>()
        for (i in 0..50) {
            dataList.add(DataItemBean("", if (i % 5 == 0) 0x10 else 0x20))
        }
        val mAdapter = MAdapter(R.layout.item_rv, dataList).apply {
            // rv 的点击事件
            setOnItemClickListener { adapter, view, position ->
                Log.d(TAG, "点击了 rv: $position")
            }
            // rv item child 的点击事件
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.btn_item -> {
                        Log.d(TAG, "点击了 rv child: $position")
                    }
                }
            }
        }
        rv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}