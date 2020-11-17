package com.zs.zs_jetpack.coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 携程demo
 */
class CoroutinesTest {

}

fun main() {
    GlobalScope.launch {
        print("线程名: " + Thread.currentThread().name)
    }
}