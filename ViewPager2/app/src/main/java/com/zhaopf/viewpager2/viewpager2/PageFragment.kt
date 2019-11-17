package com.zhaopf.viewpager2.viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.zhaopf.viewpager2.R
import kotlinx.android.synthetic.main.fragment_page.*

class PageFragment : Fragment() {

    companion object {

        fun newInstance(position1: Int): PageFragment = PageFragment().apply {
            arguments = Bundle().apply {
                putInt("position1", position1)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewpager2.apply {
            offscreenPageLimit = 3
            adapter =
                ViewPagerFragmentStateAdapter2(
                    childFragmentManager,
                    lifecycle,
                    (arguments?.getInt("position1") ?: 0),
                    arrayListOf("第1页", "第2页", "第3页")
                )
        }

        TabLayoutMediator(tabLayout, viewpager2) { tab, position ->
            tab.text = "${position + 1}"
        }.attach()
    }
}

class ViewPagerFragmentStateAdapter2(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val position1: Int,
    private val strList: List<String>
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = strList.size

    override fun createFragment(position: Int): Fragment =
        PageFragment2.newInstance(position1, position)
}