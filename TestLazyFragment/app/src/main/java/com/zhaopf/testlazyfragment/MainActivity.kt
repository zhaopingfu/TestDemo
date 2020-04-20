package com.zhaopf.testlazyfragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zhaopf.testlazyfragment.adapter.MainVpAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mFullScreenObserver by lazy { FullScreenObserver(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(vp_app_main_top_vp) {
            offscreenPageLimit = 5
            adapter = MainVpAdapter(supportFragmentManager)
        }
        with(tl_app_main_bottom_tab) {
            setTabTextColors(Color.GREEN, Color.RED)
            setupWithViewPager(vp_app_main_top_vp)
        }
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // 沉浸式
        mFullScreenObserver.applyKitKatTranslucencyWithColor(Color.BLACK)
    }
}