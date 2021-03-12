package cheatsheets.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>): Unit = runBlocking {


    // Coroutinen sind nicht an einen festen Thread gebunden,
    // sondern werden jeweils einem Thread zugeordnet.
    // Wie Threads gespwant werden, bzw. wie die Zuordnung stattfindet, lässt sich bis zu einem gewissen Grad steuern:

    // Ohne eine explizite Angabe, läuft die Coroutine im Threadpool, bzw. Thread den Basiscontext.
    // In dem Fall im Mainthread.
    launch {
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
    }

    // Der globale Scope bringt seinen eigenen Worker-Pool mit.
    GlobalScope.launch {
        println("new scope: I'm working in thread ${Thread.currentThread().name}")
    }

    // Unconfined startet die Routine im selben Context wie den Basiscontext:
    GlobalScope.launch {
        launch(Dispatchers.Unconfined) {
            println("Unconfined other scope: I'm working in thread ${Thread.currentThread().name}")
        }
    }

    // Läuft im main-Thread
    launch(Dispatchers.Unconfined) {
        println("Unconfined: I'm working in thread ${Thread.currentThread().name}")
    }

    // Läuft im Defaultpool. Äquivalent zu `GlobalScope.launch`
    launch(Dispatchers.Default) {
        println("Default: I'm working in thread ${Thread.currentThread().name}")
    }

    // Startet die Coroutine in einem eigenen single Thread:
    launch(newSingleThreadContext("MyOwnThread")) {
        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
    }

    // `newFixedThreadPoolContext` erzeugt einen eigenen Threadpool
    launch(newFixedThreadPoolContext(5, "MyOwnPool")) {
        println("newFixedThreadPoolContext: I'm working in thread ${Thread.currentThread().name}")
    }

    delay(2000)



}
