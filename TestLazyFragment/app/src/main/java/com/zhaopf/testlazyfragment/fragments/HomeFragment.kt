package com.zhaopf.testlazyfragment.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhaopf.testlazyfragment.R
import com.zhaopf.testlazyfragment.adapter.HomeVpAdapter
import com.zhaopf.testlazyfragment.databinding.FragmentHomeLayoutBinding

/**
 * @author pingfu.zhao
 * @date 2020-01-14 10:46
 */
class HomeFragment : BaseFragment<FragmentHomeLayoutBinding>() {

    private val TAG = "HomeFragment"

    private var mAdapter: HomeVpAdapter? = null

    override fun getLayoutResId(): Int = R.layout.fragment_home_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = HomeVpAdapter(childFragmentManager)

        with(binding.appHomeBottomVp) {
            offscreenPageLimit = 5
            adapter = mAdapter
        }
        with(binding.tlAppHomeTopTab) {
            setTabTextColors(Color.GREEN, Color.RED)
            setupWithViewPager(binding.appHomeBottomVp)
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

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeLayoutBinding {
        return FragmentHomeLayoutBinding.inflate(inflater, container, false)
    }
}
