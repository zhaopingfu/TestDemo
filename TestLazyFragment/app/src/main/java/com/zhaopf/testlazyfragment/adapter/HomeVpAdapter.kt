package com.zhaopf.testlazyfragment.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.zhaopf.testlazyfragment.fragments.MoreFragment
import com.zhaopf.testlazyfragment.fragments.PopularFragment
import com.zhaopf.testlazyfragment.fragments.SmallGameFragment

/**
 * @author pingfu.zhao
 * @date 2020-01-14 10:56
 */
class HomeVpAdapter(fm: FragmentManager) : BaseVpAdapter(fm) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> PopularFragment()
        1 -> SmallGameFragment()
        else -> MoreFragment()
    }

    override fun getCount(): Int = 3

    override fun getPageTitle(position: Int): CharSequence? = when (position) {
        0 -> "热门"
        1 -> "小游戏"
        else -> "更多"
    }
}