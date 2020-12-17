package com.zhaopf.testlazyfragment

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zhaopf.testlazyfragment.adapter.MainVpAdapter
import com.zhaopf.testlazyfragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mFullScreenObserver by lazy { FullScreenObserver(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.vpAppMainTopVp) {
            offscreenPageLimit = 5
            adapter = MainVpAdapter(supportFragmentManager)
        }
        with(binding.tlAppMainBottomTab) {
            setTabTextColors(Color.GREEN, Color.RED)
            setupWithViewPager(binding.vpAppMainTopVp)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // 沉浸式
        mFullScreenObserver.applyKitKatTranslucencyWithColor(Color.BLACK)
    }
}