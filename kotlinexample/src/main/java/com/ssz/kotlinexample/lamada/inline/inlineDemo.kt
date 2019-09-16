package com.ssz.kotlinexample.lamada.inline

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * @author : zsp
 * time : 2019 09 2019/9/11 10:51
 */

fun main(args: Array<String>) {

    val l : Lock = ReentrantLock()
    lock(l){

    }
    membersOf<StringBuffer>()
}

inline fun <T> lock(lock: Lock, body: () -> T){
    lock.lock()
    try {
        body()
    }finally {
        lock.unlock()
    }
}


inline fun <reified T> membersOf() = T::class.members