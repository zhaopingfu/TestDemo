package com.meta.kotlinstudy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    private val mFactory by lazy { MainViewModelFactory("11111111") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewModelProviders.of(this, mFactory).get(MainViewModel::class.java)
        ViewModelProviders.of(this, mFactory).get(MainViewModel::class.java)
        ViewModelProviders.of(this, MainViewModelFactory("2222222")).get(MainViewModel::class.java)
        ViewModelProviders.of(this, MainViewModelFactory("3333333")).get("33333", MainViewModel::class.java)
        ViewModelProviders.of(this, mFactory).get(MainViewModel::class.java)
    }
}