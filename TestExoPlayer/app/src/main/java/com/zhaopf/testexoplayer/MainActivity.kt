package com.zhaopf.testexoplayer

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val colorList = arrayListOf(Color.RED, Color.YELLOW, Color.BLUE, Color.GRAY)
        with(viewPager) {
            offscreenPageLimit = 5
            adapter = MyVpAdapter(
                supportFragmentManager,
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                colorList
            )
        }
        tabLayout.setupWithViewPager(viewPager)
    }
}

class MyVpAdapter(fm: FragmentManager, behavior: Int, private val colorList: List<Int>) :
    FragmentStatePagerAdapter(fm, behavior) {

    override fun getItem(position: Int): Fragment =
        if (position == 0) YoujiVideoFragment()
        else HomeOtherFragment.newInstance(colorList[position - 1])

    override fun getCount() = colorList.size + 1

    override fun getPageTitle(position: Int): CharSequence? = "${position + 1}"
}