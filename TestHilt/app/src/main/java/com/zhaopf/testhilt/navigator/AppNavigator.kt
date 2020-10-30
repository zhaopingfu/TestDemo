package com.zhaopf.testhilt.navigator

/**
 * @author zhaopingfu
 * @date 2020/10/23
 */

enum class Screens {
    BUTTONS,
    LOGS
}

interface AppNavigator {
    fun navigateTo(screen: Screens)
}