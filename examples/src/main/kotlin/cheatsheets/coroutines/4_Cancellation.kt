package cheatsheets.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    // Coroutinen sollten im Falle ihres äußeren Beendens
    // möglichst kooperativ handeln und darauf vorbereitet sein.
    runBlocking {
        println("Starting non coop")
        var job = nonCooperative()
        delay(1300L)
        println("Stopping non coop")
        job.cancelAndJoin()
        println("Ready")


        println("Starting coop")
        job = cooperative()
        delay(1300L)
        println("Stopping coop")
        job.cancelAndJoin()
        println("Ready")


        println("Starting implicit coop")
        job = implicitCooperative()
        delay(1300L)
        println("Stopping implicit coop")
        job.cancelAndJoin()
        println("Ready")
    }
}

// Diese Coroutine behandelt einen Stop garnicht.
// Sie wird einfach weiterlaufen, bis sie sich selbst beendet hat,
// oder hart abgebrochen wird.
fun nonCooperative() = GlobalScope.launch(Dispatchers.Default) {
    var nextPrintTime = System.currentTimeMillis()
    var i = 0

    while (i < 10) {
        if (System.currentTimeMillis() >= nextPrintTime) {
            println("non coop job: I'm sleeping ... ${i++}")
            nextPrintTime += 1000L
        }
    }
}


// Diese Coroutine behandelt ihren Zustand kooperativ und fragt das Flag
// `isActice` explizit regelmäßig ab.
fun cooperative() = GlobalScope.launch(Dispatchers.Default) {
    var nextPrintTime = System.currentTimeMillis()
    var i = 0

    while (i < 10) {
        if (!isActive) {
            return@launch
        }

        if (System.currentTimeMillis() >= nextPrintTime) {
            println("coop job: I'm sleeping ... ${i++}")
            nextPrintTime += 1000L
        }
    }
}

// Coroutinen können auch implizit ihren Zustand behandeln.
// Dabei nutzt man die Eigenschaft von Coroutine-Operationen,
// dass sie im Falle eines Beendens eine interne Exception werfen.
fun implicitCooperative() = GlobalScope.launch(Dispatchers.Default) {
    try {
        repeat(1000) { i ->
            println("implicit coop job: I'm sleeping $i ...")
            delay(500L)
        }
    } catch (e: Throwable) {
        println("Caught exception ${e.message}")
    } finally {
        println("I'm ready!")
    }
}

