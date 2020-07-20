package com.zhaopf.teststyleandtheme

import androidx.annotation.StyleRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author Flywith24
 * @date   2020/6/23
 * time   19:13
 * description
 */
class ThemeViewModel : ViewModel() {
    val primaryColor = MutableLiveData<@StyleRes Int>()
    val currentTheme = MutableLiveData<Theme>(Theme.SYSTEM)
    val edgeToEdgeEnabled = MutableLiveData<Boolean>(false)

    fun setCurrentTheme(theme: Theme) {
        // 防抖
        if (theme == currentTheme.value) return
        currentTheme.value = theme
    }
}