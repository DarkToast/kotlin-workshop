package workshop.fundamentals

// kotlinc main.kt
// kotlin workshop/basics/MainKt.class
// javap -c workshop/basics/MainKt.class

fun getHello(): String = "Hallo Welt"

fun main(args: Array<String>) {
    println(getHello())
}