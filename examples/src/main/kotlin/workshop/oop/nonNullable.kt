package workshop.oop

fun NonNullable() {
    // In Kotlin kann einer normalen Referenz kein `null` zugewiesen werden.
    var nonNullable: Int = 42
    // nonNullable = null // <-- geht nicht.s

    // Dafür gibt es `nullable`-Types. Dies sind alle Typen erweiter um ein '?'
    var nullable: Int? = 42
    nullable = null

    // Eine Zuweisung nullable = nonNullable ist jederzeit möglich
    val x: Int? = nonNullable

    // Eine Zuweisung nonNullable = nullable ist nur per vorgelagterem Null-Check möglich:
    if (nullable != null) {
        nonNullable = nullable
    }

    // Alternativ sind checks per Elvisoperator möglich:
    val y: Int = nullable ?: 42 // -> wenn null, 42


    val s: Int = nullable?.toString()?.length ?: 42 // -> wenn null, wird `toString` nicht aufgerufen.
    // Der gesamte Ausdruck bleibt `null`.

    // Mutige können den Check hart überschreiben. Hier können NullPointerExceptions auftreten!
    nonNullable = nullable!!
}

class Tariff(val baseFee: Int?)

class Contract(val tariff: Tariff)

class Customer(val contract: Contract?)
