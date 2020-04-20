package com.zhaopf.testlazyfragment.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import com.zhaopf.testlazyfragment.R
import kotlinx.android.synthetic.main.fragment_challenge_layout.*

/**
 * @author pingfu.zhao
 * @date 2020-01-14 10:46
 */
class MoreFragment : BaseFragment() {

    private val TAG = "MoreFragment"

    override fun getLayoutResId(): Int = R.layout.fragment_challenge_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_challenge.text = TAG
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