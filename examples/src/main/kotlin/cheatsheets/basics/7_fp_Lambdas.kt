package cheatsheets.basics

/**
 * Themen:
 *  - Lambdas
 *  - CollectionAPI
 */

fun main() {
    lambdas()
}

fun lambdas() {
    val aList: List<Int> = listOf(1, 2, 3, 4, 5)

    // `{ int -> int * int }` ist ein Lambdaausdruck, also eine anonyme Funktion, die wir `map` als Parameter
    // übergeben. Das `int` vor dem `->` ist dabei der erste Paramerter der Funktion. Alles was nach dem `->` steht
    // ist der Funktionskörper. Der Rückgabetyp der Funktion basiert auf dem Typ des letzten Statements.

    // `map` wendet nun die übergebene Funktion auf jedes Element der Liste an. Die Resultate der Funktion werden
    // dann in einer neuen Liste zusammengefasst und von `map` zurück gegeben.
    aList.map({ int -> int * int }) // [2, 4, 9, 16, 25]

    // `all` übernimmt ebenfalls einen Funktionslambda, erwartet allerdings, dass die Funktion als Rückgabetyp ein `Boolean`
    // angibt. Die Funktion wird dann wieder auf jedes Element angewendet. Die boolschen Ergebnisse werden dann
    // zusammengefasst und `AND` verknüpft. Somit bilden wir einen Allquantor auf die Liste.
    aList.all({ int -> int % 2 == 0 }) // false

    // `any` ist das Pendant zu `all` und bildet einen Existenzquantor. `any` gibt also `true` zurück, weil es mindestens
    // ein Element in der Liste gibt, welches, mit Modulo 2 genommen, 0 ergibt.
    aList.any { int -> int % 2 == 0 } // true

    // `fold` faltet alle Elemente der Liste zu einem Wert zusammen. Wie dies geschieht, entscheidet der übergebene Lambda.
    // In diesem Fall summieren wir alle Elemnte auf.
    // Der erste Parameter bildet den Startwert unseres Endergebnis. Das Lambda erhält nun zwei Eingabeparameter: `acc`
    // und `int`. `acc` bildet dabei den Akkumulator, der alle vorangegangenen Summen enthält, bzw. vor dem ersten Wert
    // den Startwert 0. `int` ist wiederum das aktuelle Element der Liste.
    aList.fold(0, { acc, int -> acc + int }) // 15

    // Alternative Schreibweise, wenn ein Lambda der letzte Parameter ist.
    aList.fold(0) { acc, int -> acc + int }

    // `forEach` führt das übergenene Lambda einfach auf jedes Element aus. Der Rückgabewert ist dabei egal.
    aList.forEach { int -> println(int) }
}
