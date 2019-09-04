package com.meta.kotlineverywhere2019

/**
 * 函数式编程
 */

fun Int.sample(a: Float, b: Double): Long = this * (a + b).toLong()

val function: Int.(Float, Double) -> Long = Int::sample

fun sample(a: Int.(Float, Double) -> Long): Long = 3.a(1f, 2.0)

fun main() = println(sample(function))

fun main2() = println(sample(Int::sample))