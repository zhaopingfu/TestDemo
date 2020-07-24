@file:JvmName("TestKotlin")

package com.meta.kotlinstudy.test

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.TypedValue
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.concurrent.Executors

fun testKotlin() {
    // test1()
    // test2()
    // test3(this)
    // test4(this)
    // test5()
    // mStartActivity<MainActivity>()
    // test6()
    testSequence()
}

fun test1() {
    val jack = User("Jack", 20)
    val tom = User("Tom", 20)
    val sameAge = jack sameAge tom
    println(sameAge)
}

/**
 * data class
 */
data class User(
        val name: String,
        val age: Int)

/**
 * 中缀表达式
 */
infix fun User.sameAge(user: User): Boolean {
    return this.age == user.age
}

fun test2() {
    val json = """
        {
            "city_info" : {
                "title" : "BeiJing",
                "data"  : "city is beijing."
            }
        }
    """.trimIndent()

    val jsonObject = try {
        JSONObject(json)
    } catch (e: JSONException) {
        println("---> error: ${e.message}")
        null
    }
    dealCityInfo(jsonObject) {
        println("---> failed.")
    }
}

/**
 * 标准函数
 */
fun dealCityInfo(data: JSONObject?, fail: () -> Unit) {
    data?.takeIf { it.has("city_info") }
            ?.takeIf {
                with(it.getJSONObject("city_info")) {
                    return@takeIf has("title") && has("data")
                }
            }
            ?.let { it.getJSONObject("city_info") }
            ?.apply {
                // do something
                println("---> do something.")
            } ?: fail()
}

/**
 * 扩展函数
 */
fun test3(context: Context) {
    println("---> ${1f.dp(context)}")
}

fun Float.dp(context: Context): Int {
    return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            context.resources.displayMetrics
    ).toInt()
}

/**
 * 扩展POST
 */
fun test4(activity: Activity) {
    activity.apply {
        main {
            // main thread.
            println("---> main: ${Thread.currentThread().name}")
            worker {
                // worker thread.
                println("---> worker: ${Thread.currentThread().name}")
            }
            println("---> main2: ${Thread.currentThread().name}")
        }
    }
}

fun Activity.main(todo: () -> Unit) {
    Handler().post {
        todo()
    }
}

fun Activity.worker(todo: () -> Unit) {
    Executors.newSingleThreadExecutor()
            .execute {
                todo()
            }
}

/**
 * IO
 */
fun test5() {
    javaIO()
    luckyIO()
}

fun javaIO() {
    var br: BufferedReader? = null
    try {
        br = BufferedReader(FileReader(File("readme.md")))
        var line: String
        while (true) {
            line = br.readLine() ?: break
            println("---> $line")
        }
    } catch (ignore: Exception) {
    } finally {
        try {
            br?.close()
        } catch (ignore: IOException) {

        }
    }
}

// 快乐的IO
fun luckyIO() {
    BufferedReader(FileReader(File("readme.md"))).use {
        var line: String
        while (true) {
            line = it.readLine() ?: break
            println("---> $line")
        }
    }

    // 更加精简
    File("readme.md").readLines().forEach(::println)
}

inline fun <reified T : Activity> Activity.mStartActivity() {
    startActivity(Intent(this, T::class.java))
}

/**
 * 集合类操作
 */
fun test6() {
    val numList = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
    val numList2 = arrayOf(1, 2, 3)
    val numList3 = Array<Int>(1) { it }
    // 只要有一个满足即成立
    val resultAny = numList.any { it > 5 }
    println("---> resultAny: $resultAny")

    // 所有满足才成立
    val resultAll = numList.all { it > 5 }
    println("---> resultAll: $resultAll")
}

/**
 * Sequence
 *
 * 1、当不需要中间操作时，使用List
 * 2、当仅仅只有map操作时，使用sequence
 * 3、当仅仅只有filter操作时，使用List
 * 4、如果末端操作是first时，使用sequence
 * 5、对于没有提及的其他操作符或者其他操作符的组合，请尝试使用例子去验证一下
 */
fun testSequence() {
    var time = System.currentTimeMillis()
    val list = (1..65535)
            .toList()
            .map { it * 2 }
            .filter { it % 3 == 0 }
    list.first()
    // 76ms
    println("---> list: ${System.currentTimeMillis() - time}")

    time = System.currentTimeMillis()
    val sequence = (1..65535)
            .asSequence()
            .map { it * 2 }
            .filter { it % 3 == 0 }
    sequence.first()
    // 5ms
    println("---> sequence:  ${System.currentTimeMillis() - time}")
}