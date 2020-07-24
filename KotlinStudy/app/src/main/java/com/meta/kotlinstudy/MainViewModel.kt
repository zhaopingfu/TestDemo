package com.meta.kotlinstudy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

/**
 * @author zhaopingfu
 * @date 2019-09-21 17:11
 */
class MainViewModel(private val str: String) : ViewModel() {

    init {
        println("str: $str")
    }
}

class MainViewModelProvider(owner: ViewModelStoreOwner, factory: Factory) :
    ViewModelProvider(owner, factory)

class MainViewModelFactory(private val str: String) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(String::class.java)
            .newInstance(str)
    }
}