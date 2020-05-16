package com.zhaopf.testguava

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.google.common.base.Optional
import com.zhaopf.testguava.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root)

        binding.btnGuava1.setOnClickListener {
            val data1 = Optional.fromNullable(MainBean.getTestData1())
            binding.btnGuava1.text = if (data1.isPresent) "不空" else "空"
            // binding.btnGuava1.text = MainBean.getTestData1().homeBean.bannerList[0].url;
        }
        binding.btnGuava2.setOnClickListener {
            val data2 = Optional.fromNullable(MainBean.getTestData2())
            Log.d(TAG, "onCreate data2: ${data2.get()}")
            Optional.fromNullable(MainBean.getTestData2())
                .orNull()
                ?.mainDataBean
                ?.bannerList
                ?.getOrNull(0)
                ?.url
                ?: "a"

            binding.btnGuava2.text = MainBean.getTestData2()
                ?.mainDataBean
                ?.bannerList
                ?.get(0)
                ?.url
                ?: "没数据"
        }
        binding.btnGuava3.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.btnGuava3.text = Optional.fromNullable(MainBean.getTestData3())
                    .toJavaUtil()
                    .map { it.mainDataBean }
                    .map { it.bannerList }
                    .filter { it.size > 0 }
                    .map { it[0] }
                    .map { it.url }
                    .orElse("默认值")
            }
        }
        binding.btnGuava4.setOnClickListener {
            binding.btnGuava4.text = MainBean.getTestData3()
                ?.mainDataBean
                ?.bannerList
                ?.getOrNull(0)
                ?.url
                ?: "默认值"
        }
    }
}