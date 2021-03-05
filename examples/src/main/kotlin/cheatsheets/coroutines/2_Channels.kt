package cheatsheets.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    val channel = Channel<Int>()

    GlobalScope.launch {
        for (x in 1..5) {
            channel.send(x * x)
            delay(1000L)
        }
        channel.close()
    }

    runBlocking {
        for (i in channel) {
            println(i)
        }
        println("Ready!")
    }
}

sealed class CustomerEvent
data class CreateCustomer(val name: String) : CustomerEvent()
data class UpdateCustomer(val name: String) : CustomerEvent()

fun CoroutineScope.productEvents() = produce<CustomerEvent> {
    repeat(20) {
        if (it % 2 == 0) {
            send(CreateCustomer("Max Mustermann the $it"))
        } else {
            send(UpdateCustomer("Max Musterfrau the $it"))
        }
    }
}