package com.meta.kotlinstudy.基础部分

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView

// 最简单的单例
object Singleton {
    var name: String = ""
    fun test() {}
}

fun testSingleton() {
    Singleton.name = "testName"
    Singleton.test()
}

/**
 * 在Kotlin中一般实现接口或者抽象类都是用object
 */
fun testObject() {
    val tv: TextView? = null
    tv?.setOnClickListener(object : View.OnClickListener {
        override fun onClick(v: View?) {
        }
    })
    // 如果接口只有一个方法，可以直接用大括号
    tv?.setOnClickListener { }

    tv?.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

/**
 * 有时候Android中需要全局单例Application
 */
class MyApp : Application() {

    companion object {
        lateinit var INSTANCE: MyApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}

/**
 * 可以直接声明顶层变量加 lazy
 */
val testList by lazy { ArrayList<String>() }

/**
 * 加锁的，类似java的DCL
 */
class DoubleCheckSingleton private constructor() {

    companion object {

        // kotlin 写法
        val INSTANCE_1 by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DoubleCheckSingleton()
        }

        // 翻译Java写法
        private var INSTANCE_2: DoubleCheckSingleton? = null

        // 如果要给方法加锁要用
        // @Synchronized
        fun getInstance(): DoubleCheckSingleton {
            if (null == INSTANCE_2) {
                synchronized(DoubleCheckSingleton::class) {
                    if (null == INSTANCE_2) {
                        INSTANCE_2 = DoubleCheckSingleton()
                    }
                }
            }
            // 这里用!! 表示强制转换为非空的
            return INSTANCE_2!!
        }
    }
}

/**
 * 内部类的方式
 */
class InnerStaticSingleton private constructor() {

    companion object {
        fun getInstance() = Holder.INSTANCE
    }

    private object Holder {
        val INSTANCE = InnerStaticSingleton()
    }
}