package com.zhaopf.testlazyfragment.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @author pingfu.zhao
 * @date 2020-01-14 11:28
 */
abstract class BaseVpAdapter(fm: FragmentManager) : FragmentPagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    var mCurrentPrimaryItem: Fragment? = null

    override fun setPrimaryItem(container: ViewGroup, position: Int, obj: Any) {
        super.setPrimaryItem(container, position, obj)

        if (obj is Fragment) {
            mCurrentPrimaryItem = obj
        }
    }
}