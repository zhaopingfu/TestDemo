package com.meta.kotlinstudy.基础部分

/**
 * @author pingfu.zhao
 * @date 2019-11-30 19:01
 */

class MyList<T> : List<T> by ArrayList<T>()

interface A {
    fun sayHello()
}

class Users : A {
    override fun sayHello() {
        println("Hello")
    }

    val name = "1"
}

// 我要有 A 接口的功能，但是不想自己做，交给了另一个人帮我做，就是 by  委托
class User2 : A by Users()

fun main() {
    val aaa by lazy { "" }
    User2().sayHello()
}