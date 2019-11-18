package com.meta.kotlinstudy.基础部分

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

fun main(args: Array<String>) {

}