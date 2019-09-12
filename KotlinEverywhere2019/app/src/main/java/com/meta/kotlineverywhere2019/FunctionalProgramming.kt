package com.meta.kotlineverywhere2019

/**
 * 函数式编程
 */

fun Int.sample(a: Float, b: Double): Long = this * (a + b).toLong()

val function: Int.(Float, Double) -> Long = Int::sample

fun sample(a: Int.(Float, Double) -> Long): Long = 3.a(1f, 2.0)

fun main() = println(sample(function))

fun main2() = println(sample(Int::sample))

/**
 * 成员引用
 */


class Test {
    fun functionParam(a: Int) = println(a)
}

fun function1(a: Test.(Int) -> Unit) = Test().a(6)
fun function2(a: (Int) -> Unit) = a(6)

fun test() {
    /**
     * 使用成员引用，及时用类名引用成员函数（扩展函数）
     * 此时 Test::functionParam 的类型为 Test.(Int) -> Unit
     */
    function1(Test::functionParam)

    /**
     * 使用绑定引用，即直接使用对象去引用成员函数（扩展函数）
     * 此时 test::functionParam 的类型为 (Int) -> Unit
     */
    val test = Test()
    function2(test::functionParam)
}