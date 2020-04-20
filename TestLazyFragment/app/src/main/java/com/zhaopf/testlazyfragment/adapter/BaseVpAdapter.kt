package com.zhaopf.testlazyfragment.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

/**
 * @author pingfu.zhao
 * @date 2020-01-14 11:28
 */
abstract class BaseVpAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    var mCurrentPrimaryItem: Fragment? = null

    override fun setPrimaryItem(container: ViewGroup, position: Int, obj: Any) {
        super.setPrimaryItem(container, position, obj)

        if (obj is Fragment) {
            mCurrentPrimaryItem = obj
        }
    }
}