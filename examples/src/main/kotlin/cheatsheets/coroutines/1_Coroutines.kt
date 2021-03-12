package cheatsheets.coroutines

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kotlin.system.measureTimeMillis

fun main() {

    // Der kleinste mögliche Start einer Coroutine über den GlobalScope mit `launch`
    val job: Job = GlobalScope.launch {
        // Statt eines klassischen Thread.sleep benutzt man ein delay. Blockiert nicht den Thread!
        delay(1000)
        println("Hallo Welt")
    }

    // Der `Job` ähnelt dem klassichen Thread-Konzept, blockiert aber auch bei `join` nicht meinen Hauptthread.
    // `join` muss im Kontext einer weiteren Coroutine laufen.
    // Da wir im Main-Thread sind, können wir mit `runBlocking` einen Context erzeugen und die Threads
    // synchronisieren.
    runBlocking {
        job.cancel()
        job.join()
    }

    // Um Ergebnisse aus einer Coroutine zu beziehen, startet die Coroutine mit
    // `async`. Es gibt ein Deffered zurück, was dem Konzept von "Futures" entspricht.
    val deferred: Deferred<Int> = GlobalScope.async {
        delay(1000)
        println("Async")
        13 + 29
    }

    // Auf das Ergebnis kann mit `await` gewartet werden.
    // Auch diese Methode mit im Kontext einer darüber liegenden Coroutine aufgerufen werden.
    // Da wir im Main-Thread sind, können wir mit `runBlocking` einen Context erzeugen und die Threads
    // synchronisieren.
    val result: Int = runBlocking {
        deferred.await()
    }

    println(result)

    // Ein Beispiel mit verschachtelten Funktionen:
    // Da wir im Main-Thread sind, erzeugen wir mit `runBlocking` einen neuen Context um die Threads zu
    // synchronisieren.
    runBlocking {
        // Wir benutzen `measureTimeMillis` um die gemeinsame Laufzeit zu messen.
        // Wir rufen intern die beiden Methoden hintereinander auf:
        val time = measureTimeMillis {
            // Obwohl wir suspending Functions aufrufen, starten wir sie aber nicht in ihrer eigenen Coroutine.
            // Ein automatischer asynchroner Start funtioniert also nicht.
            createIntOne()
            createIntTwo()
        }

        // Die Gesamtlaufzeit ist bei ~2s
        println("Completed in $time ms")
    }

    // Um wirklich die susending Functions asynchron zu starten, rufen wir sie in einem `async`-Block auf:
    runBlocking {
        val time = measureTimeMillis {
            // btw: Coroutinen lassen sich mit `start` ein wenig in ihrer Ausführung steuern.
            // Hier: Mit Lazy wird die Coroutine erst beim ersten Ergebniszugriff gestartet.
            val one = async(start = CoroutineStart.LAZY) { createIntOne() }
            val two = async { createIntTwo() }

            println("The answer is ${one.await() + two.await()}")
        }

        // Die Gesamtlaufzeit ist bei ~1s -> Asynchron!
        println("Completed in $time ms")
    }
}

// Suspending Functions können direkt in Coroutinen als asynchrone Funktion aufgerufen werden
// und haben intrinsisch den Coroutinenscope mit dabei. Wir können hier also direkt andere
// suspending Functione wie z.B. `delay` aufrufen.
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