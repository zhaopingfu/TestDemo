package com.meta.kotlinstudy.基础部分

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meta.kotlinstudy.MainActivity

class MyActivity : Activity() {

    private val mRecyclerView by lazy { RecyclerView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mRecyclerView.run {
            layoutManager = GridLayoutManager(context, 3).apply {
                orientation = RecyclerView.HORIZONTAL

                this@apply.spanCount

                this@run.hasFixedSize()

                // 获取外面的，要获取java的class需要 MainActivity::class.java
                this@MyActivity.startActivity(Intent(context, MainActivity::class.java))
            }
        }
    }
}

class MyException(message: String) : Exception(message)

//// throw 表达式的类型是特殊类型 Nothing.该类型没有值,而是用于标记永远不能达到的代码位置
fun fail(message: String): Nothing {
    throw MyException(message)
}

fun testException() {
    // fail("fail")

    // try-表达式的返回值是 try 块中的最后一个表达式或者是（所有）catch 块中的最后一个表达式。
    // finally 块中的内容不会影响表达式的结果
    val iStr: String? = try {
        "hello"
        throw MyException("error")
    } catch (e: MyException) {
        "catch 中的值"
        null
    } finally {
        "finally中的值"
    }
    println(iStr)

    //  下面两种方式效果一样
    // val s = iStr?.length ?: throw MyException("iStr.length is null")
    // println(s)

    // val ss = iStr?.length ?: fail("iStr.length is null")
    // println(ss)

    // val x = null            // x 具有类型 Nothing?
    // val l = listOf(null)    // l 具有类型 List<Nothing?>
}

//fun main(args: Array<String>) {
//    testException()
//}