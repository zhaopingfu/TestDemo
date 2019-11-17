package com.zhaopf.testexoplayer

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home_other.*

/**
 * 首页第一个fragment之外的内容
 */
class HomeOtherFragment : Fragment() {

    companion object {
        fun newInstance(backgroundColor: Int): HomeOtherFragment {
            return HomeOtherFragment().apply {
                arguments = Bundle().apply {
                    putInt("backgroundColor", backgroundColor)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_other, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flRootContainer.setBackgroundColor(arguments?.getInt("backgroundColor") ?: Color.RED)
    }
}