package com.zhaopf.testlazyfragment.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import com.zhaopf.testlazyfragment.R
import com.zhaopf.testlazyfragment.adapter.HomeVpAdapter
import kotlinx.android.synthetic.main.fragment_home_layout.*

/**
 * @author pingfu.zhao
 * @date 2020-01-14 10:46
 */
class HomeFragment : BaseFragment() {

    private val TAG = "HomeFragment"

    private var mAdapter: HomeVpAdapter? = null

    override fun getLayoutResId(): Int = R.layout.fragment_home_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = HomeVpAdapter(childFragmentManager)

        with(app_home_bottom_vp) {
            offscreenPageLimit = 5
            adapter = mAdapter
        }
        with(tl_app_home_top_tab) {
            setTabTextColors(Color.GREEN, Color.RED)
            setupWithViewPager(app_home_bottom_vp)
        }
    }

    override fun fetchData() {
        Log.d(TAG, "fetchData: ")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mAdapter?.mCurrentPrimaryItem?.userVisibleHint = isVisibleToUser
    }

    override fun onUiShown() {
        Log.d(TAG, "onUiShown: ")
    }

    override fun onUiHidden() {
        Log.d(TAG, "onUiHidden: ")
    }
}