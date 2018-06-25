package de.tarent.academy.kotlin.section1

fun myFirstIf(): String {
    val answer = 42

    do {
        println("Hallo Welt")
    } while (answer <= 43)


    val list = listOf(1, 2, 3, 4)

    val j = 2
    val range: Iterable<Int> = j .. 100 step 2

    for(element in range) {
        println(element)
    }



    val value: Any = "one"

    val result: String = when(value) {
        0 -> "zero"
        in 1 .. 10 -> {
            "between 1 and 10"
        }
        "one" -> value as String
        is String -> value
        else -> "something else"
    }




    return try {
        "MyValue"
        throw RuntimeException()
    } catch (e: Exception) {
        "Default"
    }

    return if(answer == 42) {
        "The question"
    } else {
        "Something else"
    }
}

fun main(args: Array<String>) {
    println("Hallo Welt")

    val intro: String = "Arguments: "
    var counter: Int = 1
    counter = 2

    var buffer = "FOobar"

    counter.toString()
    counter.equals(null)

    val i: List<Int> = listOf(1, 2, 3)
    val j: List<Int> = listOf(1, 2, 3)

    i == j

    val myString = """
        |  {
        |    "name": "$i"
        |  }
    """.trimMargin()
    println(myString)
}

/*
kotlin.*
kotlin.annotation.*
kotlin.collections.*
kotlin.comparisons.* (since 1.1)
kotlin.io.*
kotlin.ranges.*
kotlin.sequences.*
kotlin.text.*

*/