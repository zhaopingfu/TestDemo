package com.zhaopf.viewpager2.viewpager2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zhaopf.viewpager2.R
import kotlinx.android.synthetic.main.fragment_page3.*

class PageFragment3 : Fragment() {

    companion object {

        fun newInstance(strText: String, position1: Int, position2: Int): PageFragment3 =
            PageFragment3().apply {
                arguments = Bundle().apply {
                    putString("text", strText)
                    putInt("position1", position1)
                    putInt("position2", position2)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_page3, container, false)
    }

    private val TAG = "PageFragment3"
    private lateinit var mStr: String
    private var position1: Int = 0
    private var position2: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mStr = arguments?.getString("text") ?: "未知"
        position1 = arguments?.getInt("position1") ?: 0
        position2 = arguments?.getInt("position2") ?: 0
        tvText.text = "${javaClass.name} $mStr"
    }

    override fun onResume() {
        super.onResume()
        if (position1 == 0 && position2 == 0) {
            Log.d(TAG, "onResume $mStr")
        }
    }

    override fun onPause() {
        super.onPause()
        if (position1 == 0 && position2 == 0) {
            Log.d(TAG, "onPause $mStr")
        }
    }
}