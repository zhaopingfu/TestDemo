package com.zhaopingfu.customview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zhaopingfu.customview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnCustomFrameLayout.setOnClickListener {
            setContentView(R.layout.layout_custom_framelayout)
        }
        binding.btnCustomLinearLayout.setOnClickListener {
            setContentView(R.layout.layout_custom_linearlayout)
        }
        binding.btnCustomLinearLayoutHorizontal.setOnClickListener {
            setContentView(R.layout.layout_custom_linearlayout_horizontal)
        }
        binding.btnSimpleNestedScrollview.setOnClickListener {
            setContentView(R.layout.layout_simple_nested_scrollview)
        }
    }
}
