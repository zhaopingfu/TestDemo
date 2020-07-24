package com.meta.kotlinstudy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author pingfu.zhao
 * @date 2019-09-21 17:11
 */
class MainViewModel(private val str: String) : ViewModel() {
}

class MainViewModelFactory(val str: String) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        println("MainViewModelFactory#create: $str")
        return MainViewModel(str) as T
    }
}