package com.meta.kotlineverywhere2019

import java.util.*

/**
 * @author pingfu.zhao
 * @date 2019-09-21 15:26
 */
open class Fruit

class Apple : Fruit()

fun <T> fill(array: Array<T>, t: T) {
    array[0] = t
}

fun testHencoder1() {
    val array = arrayOf<Fruit>(Fruit())
    fill(array, Fruit())
    println("--> ${Arrays.toString(array)}")
    fill(array, Apple())
    println("--> ${Arrays.toString(array)}")
}

fun <T> copy(src: Array<out T>, des: Array<in T>) {
    src.forEachIndexed { index, t -> des[index] = t }
}

fun testHencoder2() {
    val src = arrayOf(Fruit())
    val des = arrayOf(Fruit())
    copy(src, des)
    println("--> 111 ${Arrays.toString(des)}")
    val src2 = arrayOf(Apple())
    val des2 = arrayOf(Fruit())
    copy(src2, des2)
    println("--> 222 ${Arrays.toString(des2)}")
}