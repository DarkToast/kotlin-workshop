package techtalk

// Native function types
typealias Selector = (String) -> (String) -> String
typealias Tuple = (Selector) -> String

fun main(args: Array<String>) {
    // Native lambda support - and a bit more readable syntax than java 8 ;-)
    val first: Selector = { v1: String -> { v2: String -> v1 }}
    val second: Selector = { v1: String -> { v2: String -> v2 }}

    val makeTuple = { v1: String -> { v2: String  ->
        { func: Selector -> func(v1)(v2) }
    }}

    val tuple: Tuple = makeTuple("Hallo")("Welt")

    tuple(first)
    tuple(second)
}