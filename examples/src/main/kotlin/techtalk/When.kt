package techtalk

fun main(args: Array<String>) {

    val value: Any = 42

    val result = when (value) {
        0 -> "zero"
        in 1..10 -> "between 1 and 10"  // Works for all ClosedRange<T>
        "one" -> value                  // Smart cast to String
        is String -> value
        else -> "something else"
    }


}

