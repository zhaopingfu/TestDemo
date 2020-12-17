package com.zhaopf.testlazyfragment

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.zhaopf.testlazyfragment.adapter.MainVpAdapter
import com.zhaopf.testlazyfragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mFullScreenObserver by lazy { FullScreenObserver(this) }

    private val binding by lazy { inflate<ActivityMainBinding>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

inline fun <reified T : ViewBinding> Activity.inflate(): T = inflateBinding<T>(layoutInflater)

inline fun <reified T : ViewBinding> Fragment.inflate(): T = inflateBinding(layoutInflater)

inline fun <reified T : ViewBinding> Dialog.inflate(): T = inflateBinding<T>(layoutInflater)

inline fun <reified T : ViewBinding> inflateBinding(layoutInflater: LayoutInflater): T {
    val method = T::class.java.getMethod("inflate", LayoutInflater::class.java)
    return method.invoke(null, layoutInflater) as T
}
