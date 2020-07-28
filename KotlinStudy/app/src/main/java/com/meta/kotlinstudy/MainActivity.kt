package com.meta.kotlinstudy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private val mFactory by lazy { MainViewModelFactory("11111111") }

    private val scope = MainScope()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // testViewModel()

        lifecycleScope.launch(Dispatchers.Main) {
            whenResumed {
                Log.d(TAG, "onCreate: whenResumed")
                val one = async(start = CoroutineStart.LAZY) { getResult(20) }
                val two = async(start = CoroutineStart.LAZY) { getResult(40) }
                tv_test.text = (one.await() + two.await()).toString()
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