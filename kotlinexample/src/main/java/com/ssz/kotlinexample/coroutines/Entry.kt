package com.ssz.kotlinexample.coroutines

import kotlinx.coroutines.*

/**
 * @author : zsp
 * time : 2019 09 2019/9/26 16:05
 */

//fun main() {
//
//}

//fun main() = runBlocking {
//    launch {
//        delay(200L)
//        println("Task from runBlocking")
//    }
//    coroutineScope {
////        创建⼀个协程作⽤域
//        launch {
//            delay(500L)
//            println ("Task from nested launch")
//        }
//        delay(100L)
////        这⼀⾏会在内嵌 launch 之前输出
//        println ("Task from coroutine scope")
//    }
////    这⼀⾏在内嵌 launch 执⾏完毕后才输出
//    println ("Coroutine scope is over")
//}


//fun main() = runBlocking {
//    repeat(100_000) {
//        // 启动⼤量的协程
//        launch {
//             delay(1000L)
//             print(".") }
//        }
//    }

//fun main() = runBlocking {
//    val job = launch {
//                try {
////                    while (true){
////                        println("job: I'm sleeping $1 ...")
////                        delay(500L)
////                    }
//                    repeat(1000) { i ->
//                        println("job: I'm sleeping $i ...")
//                        delay(500L)
//                    }
//                } finally {
//                    println("job: I'm running finally")
//                }
//            }
//      delay (1300L) // 延迟⼀段时间
//      println("main: I'm tired of waiting!")
//      job.cancelAndJoin() // 取消该作业并且等待它结束
//      println("main: Now I can quit.")
//      delay(2500)
//}

//fun main() = runBlocking {
//    println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
//    val startTime = System.currentTimeMillis()
//    val job = launch(Dispatchers.Default) {
////    val job = launch {
//        var nextPrintTime = startTime
//        var i = 0
//        println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
//        while (isActive) {
//        // ⼀个执⾏计算的循环，只是为了占⽤ CPU
//        // 每秒打印消息两次
//         if (System.currentTimeMillis() >= nextPrintTime) {
//             println("job: I'm sleeping ${i++} ...")
//             nextPrintTime += 500L
//         }
////         delay(1)
//    }
//    }
//    delay(1300L) // 等待⼀段时间
//     println("main: I'm tired of waiting!")
//     job.cancelAndJoin()
////     取消⼀个作业并且等待它结束
//     println("main: Now I can quit.")
//}

fun main() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            withContext(NonCancellable) {// 这个如果注释掉 delay 会无效
                println("job: I'm running finally")
                delay(3000L)
                println("job: And I've just delayed for 1 sec because I'm non-cancellable") }
        }
    }
    delay(1300L) // 延迟⼀段时间
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // 取消该作业并等待它结束
    println("main: Now I can quit.")
}
