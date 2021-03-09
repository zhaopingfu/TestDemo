package com.zhaopingfu.adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val listAdapter by lazy {
        BaseAdapter<BaseBean>().apply {
            addHolder(ABean().getViewType(), ATemplate())
            addHolder(BBean().getViewType(), BTemplate())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<RecyclerView>(R.id.rv).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

        val list = mutableListOf<BaseBean>()
        for (i in 0 until 20) {
            if (i % 2 == 0) {
                list.add(ABean())
            } else {
                list.add(BBean())
            }
        }
        listAdapter.addData(list)
    }
}
