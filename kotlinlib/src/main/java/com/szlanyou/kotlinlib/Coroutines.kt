package com.szlanyou.kotlinlib

import kotlinx.coroutines.*


fun main() {
//    GlobalScope.launch { // launch a new coroutine in background and continue
//        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
//        println("World!") // print after delay
//    }
//    var coroutineScope = CoroutineScope()
    println("Hello,") // main thread continues while coroutine is delayed
    Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive
}