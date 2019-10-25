package com.ssz.kotlinexample.standardfun

/**
 * @author : zsp
 * time : 2019 10 2019/10/24 18:04
 * public inline fun <T, R> T.let(block: (T) -> R): R {
 */
class Let {
    // 域函数，但是里面的it 只能访问公有变量
    fun entry(){
        Area(1L,21).let {
            it.age
//            it.id  -> 无法访问
            return
        }

        // 通常 ： Area(1L,45)?.let{  } 去 去空
    }

    inner class Area(private val id : Long,val age : Int)
}