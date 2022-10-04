package cheatsheets

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

suspend fun createIntOne(): Int {
    println("ONE")
    delay(1000L) // not blocking
    return 13
}

suspend fun createIntTwo(): Int {
    println("TWO")
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

fun main() {

    GlobalScope.launch {
        delay(1000)
        println("Hallo Welt")
    }

    val deferred: Deferred<Int> = GlobalScope.async {
        delay(1000)
        println("Async")
        13 + 29
    }

    val result = runBlocking {
        deferred.await()
    }

    println(result)

    runBlocking {
        val time = measureTimeMillis {
            createIntOne()
            createIntTwo()
        }

        println("Completed in $time ms")
    }

    runBlocking {
        val time = measureTimeMillis {
            val one = async(start = CoroutineStart.LAZY) { createIntOne() }
            val two = async { createIntTwo() }
            println("The answer is ${one.await() + two.await()}")
        }

        println("Completed in $time ms")
    }
}
