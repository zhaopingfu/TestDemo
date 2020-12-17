package com.zhaopf.testlazyfragment.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import com.zhaopf.testlazyfragment.adapter.PopularVpAdapter
import com.zhaopf.testlazyfragment.databinding.FragmentPopularLayoutBinding

/**
 * @author pingfu.zhao
 * @date 2020-01-14 10:46
 */
class PopularFragment : BaseFragment<FragmentPopularLayoutBinding>() {

    private val TAG = "PopularFragment"

    private var mAdapter: PopularVpAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = PopularVpAdapter(childFragmentManager)
        with(binding.vpVertical) {
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
