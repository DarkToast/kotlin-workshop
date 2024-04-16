@file:Suppress("UNUSED_VARIABLE", "KotlinConstantConditions", "unused")

package cheatsheets.basics

/**
 * Themen:
 *  - If-Expression
 *  - Loops
 *  - Try-Catch
 *  - Exceptions
 *  - When
 *
 *  s. https://kotlinlang.org/docs/control-flow.html
 */

fun main() {
    val x = 42
    println(checkValueClassic(x))
    println(checkValueWithExpression(x))

    whileLoop()
    forLoop()
    tryCatch()
}

/**
 * IF Statements
 */

// Kotlin IFs können auf zwei Arten geschrieben werden. Einerseits in einer klassischen, imperativen Art:
fun checkValueClassic(x: Int): String {
    if (x == 42) {
        return "Die Antwort"
    } else {
        return "Was anderes"
    }
}

// Und zweitens als eine Expression. IF hat somit einen "Rückgabewert". Dabei gilt die letzte Zeile der Blöcke als
// "return"-Wert.
// Bei der Eingabe von `42` als `x` wird "Die Antwort" von der Funktion zurück gegeben.
fun checkValueWithExpression(x: Int): String {
    return if (x == 42) {
        "Die Antwort"
    } else {
        "Was anderes"
    }
}

/**
 * Schleifen
 */

// Die `while`-loop ist in Kotlin fast identisch zu jeder bekannten imperativen Sprache.
fun whileLoop() {
    var x = 0

    do {
        println("The value is $x")
        x++
    } while (x <= 10)
}

fun forLoop() {
    // `listOf` ist eine von Kotlins Utilityfunctions um eine Liste von Werten zu erzeugen.
    val list = listOf(1, 2, 3, 4, 5)

    // Die foreach-Loop iteriert über jedes Collectionsobjekt von Kotlin. `x` fungiert hierbei als "Laufvariable",
    // welche bei jeder Iteration das nächste Objekt repräsentiert.
    for (x in list) {
        println("The value is $x")
    }

    // Kotlin kennt nur die foreach-loop. Möchte man allerdings das Verhalten einer festen Anzahl an Schleifendurchgängen
    // erzeugen, so bietet Kotlin mit einer Range eine Collectionklasse an, die mit der foreach-loop korrespondiert.
    // Als Beispiel erzeugt der Ausdruck `1 .. 10` eine Range von "1, 2, 3, 4, 5, 6, 7, 8, 9, 10"
    for (x in 1 .. 10) {
        print("$x, ")
    }
    println()

    // Für eine Abfolge von 1 bis ausschließlich 10 wird eine `..<` Range benutzt: "1, 2, 3, 4, 5, 6, 7, 8, 9"
    for (x in 1 ..< 10) {
        print("$x, ")
    }
    println()

    // Hier ein Beispiel einer Range von 1 bis 10 in 2er-Schritten mit dem Zusatz `step 2`.
    for (x in 1..10 step 2) {
        print("$x, ")
    }

    // FÜr eine Degression von z.B. 10 nach 1 wird `downTo` verwendet:
    for (x in 10 downTo 1) {
        print("$x, ")
    }
}

// ---- Exceptions und Try Catch
fun tryCatch() {

    // Als Beispiel eine Funktion, welche immer einen Fehler wirft:
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
  When ist ein kleines schweizer Taschenmesser und der große Bruder des eher bekannten `switch`-Statements aus anderen
  Sprachen.

  Damit lassen sich eine große Anzahl an Fallunterscheidungen behandeln.
  - Wertevergleiche
  - in range Vergleiche
  - Typ-Vergleiche
 */
fun usingWhen() {

    // `Any` ist der Obertyp aller Klassen in Kotlin. Jeder Typ ist also auch ein Any.
    val value: Any = 42

    // Auch `when` ist eine expression und hat einen "Rückgabewert".
    val result: String = when (value) {
        0 -> {
            "zero"
        }
        in 1..10 -> "between 1 and 10" // Works for all ClosedRange<T>
        "one" -> value.toString() // Smart cast to String
        is String -> value
        else -> "something else"
    }
}
