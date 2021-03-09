package com.zhaopingfu.adapter

import org.junit.Test

import org.junit.Assert.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    open class GenericsToken<T> {
        var type: Type = Any::class.java

        init {
            val superClass = this.javaClass.genericSuperclass
            type = (superClass as ParameterizedType).actualTypeArguments[0]
        }
    }

    @Test
    fun addition_isCorrect() {
        // 创建一个匿名内部类
        val oneKt = object : GenericsToken<BaseBean>() {}
        println(oneKt.type)

        val a = object :GenericsToken<String>(){}
        println(a.type)
    }
}
