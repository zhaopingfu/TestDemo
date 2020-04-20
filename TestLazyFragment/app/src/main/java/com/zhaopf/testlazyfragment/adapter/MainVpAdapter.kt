package com.zhaopf.testlazyfragment.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.zhaopf.testlazyfragment.fragments.*

/**
 * @author pingfu.zhao
 * @date 2020-01-14 10:56
 */
class MainVpAdapter(fm: FragmentManager) : BaseVpAdapter(fm) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> HomeFragment()
        1 -> ChallengeFragment()
        2 -> MakeMoneyFragment()
        3 -> TaskFragment()
        else -> GameLibraryFragment()
    }

    override fun getCount(): Int = 5

    override fun getPageTitle(position: Int): CharSequence? = when (position) {
        0 -> "首页"
        1 -> "挑战"
        2 -> "赚钱"
        3 -> "任务"
        else -> "游戏库"
    }
}