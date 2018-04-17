package techtalk

// type interpolation
val value: Int = 42

// if as expression
val result: String = if (value == 42) {
    "The ultimate question"
} else {
    "Something elese"
}


// try catch as expression
val exceptionResult = try {
    getCustomer()
} catch (e: RuntimeException) {
    "Recover-Customer"
}


fun getCustomer(): String {
    throw RuntimeException()
}