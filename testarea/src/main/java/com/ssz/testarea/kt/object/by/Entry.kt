package com.ssz.testarea.kt.`object`.by

import com.ssz.testarea.kt.`object`.by.attr.Example
import com.ssz.testarea.kt.`object`.by.attr.User

/**
 * @author : zsp
 * time : 2019 09 2019/9/16 14:09
 */

fun main(args: Array<String>) {
    //委托类
    val b = BaseImpl("张岱")
    Derived(b).print() // 输出 10

    // 属性委托
    val e = Example()
    println(e.p)     // 访问该属性，调用 getValue() 函数

    e.p = "Runoob"   // 调用 setValue() 函数
    println(e.p)

    // lazy
    println(e.lazy)
    println(e.lazy)

    println("------------ observable --------------")

    e.name = "zsp"
    e.name = "lqq"
    e.name = "zsslqq"

    println("------------ vetoable --------------")

    e.name2 = "lqq"
    println(e.name2)
    e.name2 = "zsp"
    println(e.name2)
    e.name2 = "zsplqq"
    println(e.name2)

    println("------------ notNull --------------")


    e.name3 = "lqq_"
    println(e.name3)
//    e.name3 = null
    println(e.name3)

    println("------------ map 委托，就是用 map 来存储成员变量的值 --------------")
    val user = User(mapOf(
        "name" to "zsp",
        "age" to 18,
        "gender" to  "男")
    )

    println("name = " + user.name + "  age = " + user.age + " gender + " + user.gender)



}