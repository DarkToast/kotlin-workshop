package de.tarent.academy.kotlin.section1

fun area(a: Int, b: Int = 5): Int {
    return a * b
}

fun main(args: Array<String>) {
    val field1 = area(10, 20)
    val field2 = area(5)
    val field3 = area(b = 2, a = 3)


    var notNullable: String = "Hallo"


    var nullable: String? = null

    // nullable = notNullable
    // notNullable = nullable

    if(nullable != null) {
        notNullable = nullable
    }

}