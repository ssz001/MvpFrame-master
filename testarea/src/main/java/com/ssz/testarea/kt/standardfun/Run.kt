package com.ssz.testarea.kt.standardfun

import com.ssz.testarea.kt.`object`.dataclass.Student

/**
 * @author : zsp
 * time : 2019 08 2019/8/23 17:00
 *
 * public inline fun <R> run(block: () -> R): R{}
 * public inline fun <T, R> T.run(block: T.() -> R): R {}
 *
 */
class Run {
    val lamada : (() -> String)? = null
    val student : Student? = null

    fun entry(){
        val str = lamada.run { this }
        println(str)

        val stu = student
        val sr = stu.run {
            this?.age
            this?.name
        }

       var s = run {
           for (index in 0 until 1000) {

           }
        }
    }
}