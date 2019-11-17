package com.zhaopf.testexoplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_youji_video.*

/**
 * A simple [Fragment] subclass.
 */
class YoujiVideoFragment : Fragment() {

    private val mDataList: MutableList<VideoBean> by lazy { ArrayList<VideoBean>() }
    private lateinit var mVpAdapter: ViewPagerFragmentStateAdapter3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_youji_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mVpAdapter = ViewPagerFragmentStateAdapter3(childFragmentManager, lifecycle, mDataList)

        vpVideo.apply {
            orientation = ViewPager2.ORIENTATION_VERTICAL
            offscreenPageLimit = 4
            adapter = mVpAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    swipeRefreshLayout.isEnabled = position == 0

                    if (position == mDataList.size - 1) {
                        getData(1)
                    }
                }
            })
        }
        swipeRefreshLayout.setOnRefreshListener { getData(0) }

        getData(0)
    }

    private fun getData(page: Int) {
        mDataList.apply {
            if (page == 0) {
                clear()
            }
            addAll(getVideoList())
            mVpAdapter.notifyDataSetChanged()
            if (page == 0) {
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }
}

class ViewPagerFragmentStateAdapter3(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val videoList: List<VideoBean>
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = videoList.size

    override fun createFragment(position: Int): Fragment =
        VideoItemFragment.newInstance(videoList[position])
}