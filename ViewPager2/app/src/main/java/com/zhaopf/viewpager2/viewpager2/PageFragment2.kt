package com.zhaopf.viewpager2.viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.zhaopf.viewpager2.R
import kotlinx.android.synthetic.main.fragment_page2.*

class PageFragment2 : Fragment() {

    companion object {

        fun newInstance(position1: Int, position2: Int): PageFragment2 = PageFragment2().apply {
            arguments = Bundle().apply {
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
        return inflater.inflate(R.layout.fragment_page2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewpager2.apply {
            orientation = ViewPager2.ORIENTATION_VERTICAL
            offscreenPageLimit = 4
            adapter =
                ViewPagerFragmentStateAdapter3(
                    childFragmentManager,
                    lifecycle,
                    (arguments?.getInt("position1") ?: 0),
                    (arguments?.getInt("position2") ?: 0),
                    arrayListOf(
                        "第1页",
                        "第2页",
                        "第3页",
                        "第4页",
                        "第5页",
                        "第6页",
                        "第7页",
                        "第8页",
                        "第9页",
                        "第10页"
                    )
                )
        }
    }
}

class ViewPagerFragmentStateAdapter3(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val position1: Int,
    private val position2: Int,
    private val strList: List<String>
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = strList.size

    override fun createFragment(position: Int): Fragment =
        PageFragment3.newInstance(
            strList[position],
            position1,
            position2
        )
}