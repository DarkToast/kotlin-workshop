package cheatsheets.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        // Bei `launch` wird eine Exception direkt behandelt,
        // die Coroutine gestoppt und die Exception auf der Konsole ausgeben.
        val job = GlobalScope.launch { exception1() }

        // `async` gibt ein `deffered`-Result zurück. Die Exception
        // wird daher genau so wie ein Ergebnis im `deffered` behandelt.
        val deffered = GlobalScope.async { exception1() }

        try {
            // Die deffered Exception wird hier geworfen.
            // deffered.await()
            job.join()
        } catch (e: Exception) {
            println("Ouh oh! ${e.message}")
        }

        // Ein expliziter Exception Handler kann Exception in Coroutinen fanden und verarbeiten lassen:
        val handler = CoroutineExceptionHandler { context, ex ->
            println("Caught exception in handler ${ex.message} with supressed ${ex.suppressed.joinToString(", ") { it.message!! }}")
        }

        // Dieser `launch` Ausdruck wird den Exceptionhandler im Fall einer Exception aufrufen
        GlobalScope.launch(handler) {
            exception1()
        }

        // Im Fall, dass unsere Coroutine, zwei weitere Coroutinen startet
        GlobalScope.launch(handler) {
            joinAll(launch { exception1() }, launch { exception2() })
        }

        delay(5000)
    }
}

fun CoroutineScope.exception1() = async {
    delay(500)
    throw IllegalArgumentException("I'm exception 1")
}

fun CoroutineScope.foobar() = actor<Int> {
    delay(500)
    throw IllegalArgumentException("I'm exception 1")
}

fun CoroutineScope.exception2() = async {
    try {
        delay(2000)
    } finally {
        throw RuntimeException("I'm exception 2")
    }
}