package de.tarent.academy.kotlin.section1

import session1.JavaCustomer

fun main(args: Array<String>) {
    val customer: JavaCustomer = JavaCustomer()
    val baseFee: Double = customer.getContract()?.getBaseFee() ?: 0.0

    // val baseFee2: Double = customer.getContract()!!.getBaseFee()!!

    var notNullable: String = "Hallo"
    var nullable: String? = null

    // notNullable = nullable!!

    println(baseFee)

    val foo = Foo()
    foo.myFunction()

    Bar.myStaticStuff()
}


class Foo {
    fun myFunction() {
        println("Hallo Welt")
    }
}

object Bar {
    fun myStaticStuff() {
        println("I'm static")
    }
}