package cheatsheets.coroutines

fun main() {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8)
        list.filter {
            print("filter ")
            it % 2 == 0
        }
        .map {
            print("map ")
            it * it
        }
        .forEach {
            print("foreach ")
        }

    println()
    println()

    val sequence = list.asSequence()
        .filter {
            print("filter ")
            it % 2 == 0
        }
        .map {
            print("map ")
            it * it
        }
        .forEach {
            print("foreach ")
        }

}