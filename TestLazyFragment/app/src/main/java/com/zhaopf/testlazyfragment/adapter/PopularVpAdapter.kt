package com.zhaopf.testlazyfragment.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.zhaopf.testlazyfragment.fragments.*

/**
 * @author pingfu.zhao
 * @date 2020-01-14 10:56
 */
class PopularVpAdapter(fm: FragmentManager) : BaseVpStateAdapter(fm) {

    override fun getItem(position: Int): Fragment = VideoItemFragment().apply {
        arguments = Bundle().apply {
            putString("TAG", "视频: ${position + 1}")
        }
    }

    override fun getCount(): Int = 10

    override fun getPageTitle(position: Int): CharSequence? = "视频: ${position + 1}"
}