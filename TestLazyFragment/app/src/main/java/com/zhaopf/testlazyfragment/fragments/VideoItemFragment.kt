package com.zhaopf.testlazyfragment.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhaopf.testlazyfragment.R
import com.zhaopf.testlazyfragment.databinding.FragmentChallengeLayoutBinding

/**
 * @author pingfu.zhao
 * @date 2020-01-14 10:46
 */
class VideoItemFragment : BaseFragment<FragmentChallengeLayoutBinding>() {

    private var TAG = "VideoItemFragment"

    override fun getLayoutResId(): Int = R.layout.fragment_challenge_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TAG = arguments?.getString("TAG") ?: "VideoItemFragment"
        binding.btnChallenge.text = TAG
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

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChallengeLayoutBinding {
        return FragmentChallengeLayoutBinding.inflate(inflater, container, false)
    }
}
