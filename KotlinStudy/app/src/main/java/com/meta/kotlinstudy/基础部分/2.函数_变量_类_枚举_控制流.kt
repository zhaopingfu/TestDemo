package com.meta.kotlinstudy.基础部分

/**
 * 直接将方法写在文件里面，称为顶层函数，这里不用加public，默认就是public
 * 函数接收一个 String 数组
 *
 * 返回值类型默认是Unit，相当于java中的 void
 *
 * 这里返回值写了Int，表示返回值是Int，Kotlin中只有大写的类型，没有java中的Int等小写的基础数据类型
 */
//fun main(args: Array<String>): Int {
//    println(max(1, 2))
//    return 1
//}

/**
 * 定义方法
 *
 * 这里的返回值可以进行推断出来，可以省略
 *
 * 方法参数可以有默认值
 *
 * 这里 if 语句的最后一行默认当做返回值
 */
fun max(x: Int = 0, y: Int): Int {
    return if (x > y) x else y
}


/**
 * 顶层变量，声明变量可以省略类型，可以推断出来
 */

// 只有get, final
val name = "Jack"
val name2: String = "Tom"
val name3: String? = null
val name4
    get() = "Jett"


// 有 set/get
var age = 20
var age2: Int = 20
var age3 = 0
    get() {
        return field
    }
    set(value) {
        field = value
    }

fun testField() {
    // 声明变量可以
    var nickName = "Jason"
    // 字符串拼接使用 $，这里的获取值是静态获取，当走完这一行之后就已经确定了
    // 哪怕在后面修改了nickName的值，str的值也不会改变
    val str = "name: $name age: $age nickName: $nickName"
    println(str)
    nickName = "Danny"
    println("nickName: $nickName str: $str")
}

/**
 * 这里的参数如果不加可见性修饰符就只是作为参数，否则是类的成员
 *
 * 如果类里面没有函数，可以省略类的大括号
 */
class Person(var name: String)


/**
 * enum 是一个软关键字，只有跟在 class 前面才是一个关键字
 */

// 这里不是关键字
val enum = 1

enum class Color(val r: Int, val g: Int, val b: Int) {
    RED(255, 0, 0),
    GREEN(1, 2, 3),
    BLUE(3, 4, 5);

    // 如果要在枚举中定义函数，需要用分好将上面的常量和方法分隔开
    // 这可能是kotlin中唯一需要写分号的地方了

    fun rgb() = r + g + b
}


/**
 * 控制语句
 */
fun testControlStatement(color: Color) {
    // Java 中的 switch 被换成了 when

    when (color) {
        Color.RED -> println("red")
        Color.GREEN, Color.BLUE -> println("green or blue")
        // 如果有多行，可以加大括号
        else -> {
            println("other")
        }
    }

    when {
        1 + 1 == 3 -> println("11")
        test() -> println("22")
        else -> println("33")
    }
}

fun test() = true

fun max2(x: Int = 0, y: Int): Int = when {
    x > y -> x
    else -> y
}

fun testFor() {
    // 这里的区间是 [0, 10]，注意两边都是闭区间，总共执行11次循环
    for (i in 0..10) {
    }

    // [0, 10)
    for (i in 0 until 10) {
    }

    // 从 0 到 10，每次 +2
    for (i in 0 until  10 step 2) {
    }

    // 从 10 到 0，每次 -2
    for (i in 10 downTo 0 step 2) {
    }

    // 这里的 0..10 是一个 IntRange
    val intRange: IntRange = 1..10

    // 跳出
    loop@ for (i in 0..10) {
        if (i == 2) {
            continue@loop
        }
        if (i == 5) {
            break@loop
        }
    }
}