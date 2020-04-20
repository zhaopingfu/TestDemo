package com.zhaopf.testlazyfragment.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup

/**
 * @author pingfu.zhao
 * @date 2020-01-14 11:28
 */
abstract class BaseVpStateAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    var mCurrentPrimaryItem: Fragment? = null

    override fun setPrimaryItem(container: ViewGroup, position: Int, obj: Any) {
        super.setPrimaryItem(container, position, obj)

        if (obj is Fragment) {
            mCurrentPrimaryItem = obj
        }
    }
}