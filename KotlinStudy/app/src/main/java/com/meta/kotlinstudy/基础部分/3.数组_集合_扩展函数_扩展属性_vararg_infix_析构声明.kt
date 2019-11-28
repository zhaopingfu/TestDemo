package com.meta.kotlinstudy.基础部分

import android.util.Log
import android.widget.Toast

fun testArray() {
    // 基础类型的数组要使用 intArrayOf 或者 floatArrayOf 等

    // int[]
    val array = intArrayOf(1, 2, 3)
    // Integer[]
    val array1 = arrayOf(1, 2, 3)
    // Integer[]
    val array2 = Array(3) { it }

    // 取值
    val num1 = array[0]
    val num2 = array.last()
    val num3 = array.component1()
    val num4 = array.elementAt(0)

    // 遍历
    for (i in array) {

    }

    array.forEach {
        // 这里默认参数名是it，可以自己改  x ->
        println(it)
    }

    array.forEachIndexed { index, value ->
        println("index: $index value: $value")
    }

    // 转为String
    array.joinToString()
}

fun testList() {
    val list = listOf<Int>(1, 2, 3)
    val list2 = arrayListOf<Int>(1, 2, 3)

    // 这里的 List 只能进行读取，不能进行增删
    val list3: List<Int> = listOf()

    // 可变的集合可以随意的增删
    val list4: MutableList<Int> = mutableListOf()

    list4.filter { it > 3 }
        .take(3)
        .map { "$it" }
        .flatMap { it.toCharArray().toList() }
        .sorted()

    // 一个满足就返回
    list4.any { it > 5 }
    // 所有满足的
    list4.all { it > 5 }
    list4.min()
    list4.max()
    list4.sum()
}

/**
 * 扩展方法
 * 比如一般用到字符串都会写一个StringUtil，这个时候就可以用扩展函数替代
 *
 * inline 表示在编译之后会把inline方法里面的内容复制到调用方法的地方
 */
inline fun String.show() {
    Log.d("xxx", "show ${toString()}")
}

/**
 * 扩展属性
 */
val String.lastChar: Char
    get() = get(length - 1)

/**
 * 可变参数: vararg 伪关键字，和 emum 一样
 */
fun test(vararg str: String) {
    val vararg = str[0]
    str.size
    println(str.joinToString(","))
}

/**
 * 方法默认值
 */
fun testFunc(name: String = "", sex: String = "") {
}

fun testFunc2() {
    // 这个时候传参默认是会给到name
    testFunc("男")
    // 如果想给到sex怎么办呢
    testFunc(sex = "男")

    // 通过这样指定参数名的方式，可以节省查看方法参数的时间，而且更加规范
}

/**
 * 中缀表达式
 */
infix fun Int.加(that: Int): Int = this + that

fun testExtensionFunction() {
    "abc".show()
    "abc".lastChar

    // 中缀调用 infix: 1 to 1
    val x = 1 加 1
}

fun testMap() {
    val map = mapOf<Int, Int>()
    val map2 = mapOf(Pair(1, 1), Pair(2, 2))
    val map3 = mapOf(1 to 1, 2 to 2, "c" to 2)

    map3[1]
    map3["c"]
}

data class User(
    val name: String,
    val age: Int
)

fun test析构() {
    // 从集合析构
    val xxx = "androidx.recyclerview:recyclerview:1.1.0"
    val (group, name, version) = xxx.split(":")

    // 从map析构
    val map = mapOf<Int, Int>(1 to 1, 2 to 2, 3 to 3)
    for ((k, v) in map) {
    }
    map.mapValues {
    }
    map.mapValues { (k, v) ->
    }

    // 从这里析构
    val (sex, age) = Pair("男", 1)
    val (a, b, c) = Triple("a", 2, Color.RED)

    // 从对象析构
    val user = User("name", 20)
    val (name2, age2) = user

    val userArray = arrayOf<User>()
    for ((name3, age3) in userArray) {

    }
}