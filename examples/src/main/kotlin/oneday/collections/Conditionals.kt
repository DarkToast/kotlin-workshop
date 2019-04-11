package oneday.collections

/**
 * Themen:
 *  - If-Expression
 *  - Loops
 *  - Try-Catch
 *  - Exceptions
 *  - When
 */

fun main(args: Array<String>) {
    val x = 42
    println(checkValueClassic(x))
    println(checkValueWithExpression(x))

    whileLoop()
    forLoop()
    tryCatch()
}

// ---- IFs

// Kotlin IFs können auf zwei Arten geschrieben werden. Einerseits in einer klassischen, imperativen Art:
fun checkValueClassic(x: Int): String {
    if(x == 42) {
        return "Die Antwort"
    } else {
        return "Was anderes"
    }
}

// Und zweitens als eine Expression. IF hat somit einen Rückgabewert. Dabei gilt die letzte Zeile der Blöcke als
// "return"-Wert.
fun checkValueWithExpression(x: Int): String {
    return if(x == 42) {
        "Die Antwort"
    } else {
        "Was anderes"
    }
}


// ---- Loops

// Die `while`-loop ist in Kotlin zur Abwechslung mal wie in jeder anderen Sprache. ;-)
fun whileLoop() {
    var x = 0

    while(x <= 10) {
        println("The value is $x")
        x++
    }
}

fun forLoop() {
    // `listOf` ist eine von Kotlins Utilityfunctions
    val list = listOf(1, 2, 3, 4, 5)

    // for each loop
    for(x in list) {
        println("The value is $x")
    }

    // for loop mit einer Iteratorvariable. Das Konstrukt basiert auf dem `ClosedRange` interface. Jedes Element, welches
    // dieses Interface implementiert, kann auch mit den Kotlin-Forloops benutzt werden. Für Int, Long und Char gibt es
    // bereits Implementierungen.
    for(x in 1 .. 10) {
        print("$x, ")
    }

    // Hier ein Beispiel einer Range von 1 bis 10 mit 2er Schritten. Wie das ganze programmatisch funktioniert, ist aber
    // Thema in einer advanced session.
    for(x in 1 .. 10 step 2) {
        print("$x, ")
    }
}

// ---- Exceptions und Try Catch

fun tryCatch() {

    fun getCustomerName() {
        throw RuntimeException("Hallo Welt")
    }

    // Try Catch funktioniert ebenfalls wie IFs in einem eher klassischen imperativen Sinne, aber auch als
    // Expression, sodass `try` einen Rückgabewert hat und man sich so eine schnelle Fallbacklösung bei Exception
    // erarbeiten kann.
    val name = try {
        getCustomerName()
    } catch (e: RuntimeException) {
        "N/A"
    }

    println(name)
}


// ---- When

/*
  When ist ein kleines schweizer Taschenmesser und der große Bruder des java-`switch`.
  Aber trotzdem der kleine Bruder vom Scala `match` ;-)

  Damit lassen sich eine große Anzahl an Fallunterscheidungen behandeln.
  - Wertevergleiche
  - in range Vergleiche
  - Typvergleiche
 */
fun usingWhen() {

    // `Any` ist der große Obertyp aller Klassen in Kotlin. Äquivalent zum Java `Object`
    val value: Any = 42

    // Auch `when` ist eine expression und hat einen Rückgabwert.
    val result = when (value) {
        0 -> "zero"
        in 1..10 -> "between 1 and 10"  // Works for all ClosedRange<T>
        "one" -> value                  // Smart cast to String
        is String -> value
        else -> "something else"
    }
}