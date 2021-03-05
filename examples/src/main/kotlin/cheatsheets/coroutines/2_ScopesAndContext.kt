package cheatsheets.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>): Unit = runBlocking {
    launch { // context of the parent, main runBlocking coroutine
        println("main runBlocking        : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
        println("Unconfined              : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
        println("Default                  : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
        println("newSingleThreadContext   : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(newFixedThreadPoolContext(5, "MyOwnPool")) { // will get its own new thread
        println("newFixedThreadPoolContext : I'm working in thread ${Thread.currentThread().name}")
    }
}
