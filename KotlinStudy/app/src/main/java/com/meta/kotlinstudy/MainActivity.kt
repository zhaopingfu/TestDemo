package com.meta.kotlinstudy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import coil.load
import com.meta.kotlinstudy.coroutine_retrofit.WanAndroidService
import com.meta.kotlinstudy.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*
import retrofit2.Retrofit


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    private val mFactory by lazy { MainViewModelFactory("11111111") }
    private val mainViewModel by lazy { mFactory.create(MainViewModel::class.java) }

    private val scope = MainScope()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivTest.load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606727167909&di=7694452bf754d33e6e38a634aeaa80a4&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F13%2F41%2F01300000201800122190411861466.jpg")

        // testViewModel()

        lifecycleScope.launch(Dispatchers.Main) {
            whenResumed {
                Log.d(TAG, "onCreate: whenResumed")
                val one = async(start = CoroutineStart.LAZY) { getResult(20) }
                val two = async(start = CoroutineStart.LAZY) { getResult(40) }
                binding.tvTest.text = (one.await() + two.await()).toString()
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            (1..10).asFlow().flowOn(Dispatchers.IO)
                .catch { }
                .onCompletion { Log.d(TAG, "onCreate: onCompletion") }
                .filter { it >= 5 }
                .map { "${it * it} money." }
                .collect { Log.d(TAG, "onCreate: it: $it") }
//            whenResumed {
//                createFlow()
//                    .collect { num ->
//                        Log.d(TAG, "onCreate: num: $num")
//                    }
//            }

            val flow1 = flow<Int> {
                while (true) {
                    delay(5000)
                    emit(1)
                }
            }
            val flow2 = flow<Int> {
                while (true) {
                    delay(5000)
                    emit(1)
                }
            }
            flow1.zip(flow2) { t1, t2 -> "$t1 $t2" }
                .collect {
                    Log.d(TAG, "onCreate: test: $it")
                }
        }

        lifecycleScope.launch {
            val channel = Channel<Int>()

            launch {
                for (i in 1..5) {
                    delay(200)
                    channel.send(i)
                }
                channel.close()
            }

            launch {
                for (i in channel) {
                    Log.d(TAG, "onCreate: channel value: $i")
                }
            }
        }

        lifecycleScope.launch {
            val channel = produce<Int> {
                for (i in 1..5) {
                    delay(200)
                    send(i * i)
                }
                close()
            }
            launch {
                for (i in channel) {
                    Log.d(TAG, "onCreate: channel produce value: $i")
                }
            }
        }

        mainViewModel.bannerList.observe(this, { list ->
            list.forEach { item ->
                Log.d(TAG, "onCreate: ${item.title}")
            }
        })
        binding.tvTestCoroutineRetrofit.setOnClickListener {
            mainViewModel.getBannerList()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun createFlow(): Flow<Int> = flow {
        for (i in 1..10) {
            emit(i)
        }
    }

    private suspend fun getResult(num: Int): Int = withContext(Dispatchers.IO) {
        delay(10_000)
        return@withContext num * num
    }

    private fun testViewModel() {
        mFactory.create(MainViewModel::class.java)
        mFactory.create(MainViewModel::class.java)
        mFactory.create(MainViewModel::class.java)
        mFactory.create(MainViewModel::class.java)
        mFactory.create(MainViewModel::class.java)

        MainViewModelFactory("2222222").create(MainViewModel::class.java)

        MainViewModelProvider(this, MainViewModelFactory("3333333"))
            .get("33333", MainViewModel::class.java)
    }
}