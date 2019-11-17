package com.zhaopf.viewpager2.viewpager2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.zhaopf.viewpager2.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewpager2.apply {
            offscreenPageLimit = 5
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            adapter = ViewPagerFragmentStateAdapter(
                supportFragmentManager,
                lifecycle,
                5
            )
        }

        TabLayoutMediator(tabLayout, viewpager2) { tab, position ->
            tab.text = "${position + 1}"
        }.attach()
    }
}

class ViewPagerFragmentStateAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val size: Int
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = size

    override fun createFragment(position: Int): Fragment =
        PageFragment.newInstance(position)
}