package com.zhaopf.testlazyfragment.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import com.zhaopf.testlazyfragment.R
import com.zhaopf.testlazyfragment.adapter.PopularVpAdapter
import kotlinx.android.synthetic.main.fragment_popular_layout.*

/**
 * @author pingfu.zhao
 * @date 2020-01-14 10:46
 */
class PopularFragment : BaseFragment() {

    private val TAG = "PopularFragment"

    private var mAdapter: PopularVpAdapter? = null

    override fun getLayoutResId(): Int = R.layout.fragment_popular_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = PopularVpAdapter(childFragmentManager)
        with(vp_vertical) {
            offscreenPageLimit = 6
            adapter = mAdapter
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mAdapter?.mCurrentPrimaryItem?.userVisibleHint = isVisibleToUser
    }

    override fun fetchData() {
        Log.d(TAG, "fetchData: ")
    }

    override fun onUiShown() {
        Log.d(TAG, "onUiShown: ")
    }

    override fun onUiHidden() {
        Log.d(TAG, "onUiHidden: ")
    }
}