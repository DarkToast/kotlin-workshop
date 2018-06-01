package session4

/**
 * Kotlin erlaubt es, eine begrenzte Anzahl von Operatoren wie `+`, `-`, `*`, `/`, `++`, `!`, etc.
 * in eigenen Klassen zu überladen.
 *
 * Die "klassische Workshopklasse" `Rational` soll `+` und `++` unterstützen.
 */
class Rational(val numerator: Int, val denumerator: Int) {

    /**
     * Alle Operatoren haben fest zugeordnete Methodennamen. Für `+` z.B. `plus` und dem `operator` modifier.
     * Andere Operatoren sind hier (https://kotlinlang.org/docs/reference/operator-overloading.html) zu finden.
     *
     */
    operator fun plus(b: Int): Rational = Rational(numerator + (b * denumerator), denumerator)

    operator fun plus(b: Rational): Rational = Rational(
        numerator = numerator * b.denumerator + b.numerator * denumerator,
        denumerator = denumerator * b.denumerator
    )

    /**
     * `inc` ist der Methodenpendant zu `++`.
     */
    operator fun inc() = Rational(numerator + 1, denumerator)

    override fun toString(): String {
        return """
              | $numerator
              | ---
              | $denumerator
        """.trimMargin()
    }
}

fun main(args: Array<String>) {
    println(Rational(1, 3) + Rational(2, 3))

    println(Rational(1, 3) + 1)

    var r = Rational(1, 3)
    println(++r)
}