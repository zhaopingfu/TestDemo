package com.zhaopf.testhilt.navigator

import androidx.fragment.app.FragmentActivity
import com.zhaopf.testhilt.R
import com.zhaopf.testhilt.ui.ButtonFragment
import com.zhaopf.testhilt.ui.LogFragment
import javax.inject.Inject

/**
 * @author zhaopingfu
 * @date 2020/10/23
 */
class AppNavigatorImpl @Inject constructor(private val activity: FragmentActivity) : AppNavigator {
    override fun navigateTo(screen: Screens) {
        val fragment = when (screen) {
            Screens.BUTTONS -> ButtonFragment()
            Screens.LOGS -> LogFragment()
        }

        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }
}