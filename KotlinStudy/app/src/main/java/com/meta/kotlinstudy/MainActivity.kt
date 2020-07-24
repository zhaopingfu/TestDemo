package com.meta.kotlinstudy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val mFactory by lazy { MainViewModelFactory("11111111") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mFactory.create(MainViewModel::class.java)
        mFactory.create(MainViewModel::class.java)
        mFactory.create(MainViewModel::class.java)
        mFactory.create(MainViewModel::class.java)
        mFactory.create(MainViewModel::class.java)

        MainViewModelFactory("2222222").create(MainViewModel::class.java)

        MainViewModelProvider(this, MainViewModelFactory("3333333"))
            .get("33333", MainViewModel::class.java)
    }
}