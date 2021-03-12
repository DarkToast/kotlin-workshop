package cheatsheets.coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.pow

fun main() {

    // Channels funktionieren als blocking queue.
    // Äquivalent zu z.B. Go Channels.
    val channel = Channel<Double>()


    runBlocking {
        val job = GlobalScope.launch {
            launch { produce(1, channel) }
            launch { receive(1, channel) }
        }
        job.join()

        fanOut().join()
        fanIn().join()
    }
}

// Produce übernimmt den Channel als `SendChannel` und schickt regelmäßig neue Events hinein.
// Nach 10 Events wird der Channel geschlossen
suspend fun produce(id: Int, c: SendChannel<Double>) {
    for (x in 1..10) {
        val value = 2.0.pow(x)
        println("Producer $id sends $value")
        c.send(value)
        delay(500L)
    }
    c.close()
}

// Receive übernimmt den Channel als `ReceiveChannel` und empfäng die Nachrichten.
// Ist der Channel geschlossen, beendet sich die Schleife.
suspend fun receive(id: Int, c: ReceiveChannel<Double>) {
    for (i in c) {
        println("Receiver $id got message $i")
    }
    println("Ready!")
}

// --------------------

// Fan-Out
// Ein Producer für n Receiver. Nachrichten werden fair über alle Receiver verteilt.
suspend fun fanOut() = GlobalScope.launch {
    val channel = Channel<Double>()
    launch { produce(1, channel) }
    launch { receive(1, channel) }
    launch { receive(2, channel) }
    launch { receive(3, channel) }
    launch { receive(4, channel) }
}

// Fan-In
// n Producer auf einen Receiver. Der Receiver erhält alle Nachrichten.
suspend fun fanIn() = GlobalScope.launch {
    val channel = Channel<Double>()
    launch { produce(1, channel) }
    launch { produce(2, channel) }
    launch { produce(3, channel) }
    launch { receive(1, channel) }
}
