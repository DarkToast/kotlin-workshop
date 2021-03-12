package cheatsheets.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        controlledCatch()
    }
}

// Wird eine Coroutine beendet,
// so werden auch alle parallelen Coroutinen beendet!
suspend fun naiveCatch() {
    try {
        val job = GlobalScope.launch {
            launch { exceptionalSubroutine() }
            launch { workingSubroutine() }
        }
        job.join()
    } catch (e: Throwable) {
        println("Caught exception ${e.message}")
    }
}

// Um die Kontrolle darüber zu behalten,
// können Exceptionhandler in Kobination mit Closure-Variablen
// genutzt werden.
suspend fun controlledCatch() {
    var error: Throwable? = null

    val handler = CoroutineExceptionHandler { _, t ->
        error = t
    }

    val job = GlobalScope.launch(handler) {
        launch { exceptionalSubroutine() }
        launch { workingSubroutine() }
    }
    job.join()

    if(error != null) {
        println("Ouh oh! Something went wrong. Now I can handle it.")
    }

}

suspend fun workingSubroutine() {
    repeat(10) {
        println("Iteration $it")
        delay(100)
    }
}

suspend fun exceptionalSubroutine() {
    println("Starting subroutines")
    delay(500)
    throw RuntimeException("Throw subroutine!")
}


