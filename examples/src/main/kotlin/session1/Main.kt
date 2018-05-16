package session1

/**
 * Themen:
 *  - main function
 *  - val vs. var
 *  - Alles sind Objekte
 */


/* Kotlin erlaubt stand alone functions, sodass wir die main-Methode als einzelne Funktion im der Datei definieren können.
   Schauen wir sie uns ein wenig genauer an, um ein wenig die Syntax von Kotlin zu verstehen.

   Zunächst fällt das `fun`-Wort auf. Damit werden alle Funktionen/Methoden in Kotlin eingeleitet. Warum das nötig ist,
   werden wir sehen, wenn wir uns um Typenzuweisungen kümmern.

   Als nächstes folgt der Funktionsname `main` und die Argumentenliste. Hier fällt als erstes auf, dass in Kotlin
   zunächst der Variablenname und erst dann, mit `:` getrennt, der Typ geschrieben wird. Also `name: Type`. Generell
   werden in Kotlin alle Typen immer hinten angestellt.

   So auch der Rückgabetyp `Unit` der Funktion `main`. `Unit` ist in Kotlin das Äquivalent zu `void` in Java oder C.
*/
fun main(args: Array<String>): Unit {

    // `val` definiert ein "immutable Value". Wie `final`-Variablen in Java.
    val intro: String = "Arguments: "

    // `var` definiert eine "mutable Variable". Generell gilt: So viel `val` wie möglich, so viel `var` wie nötig.
    // Ein Wort zu `Int, Double, Float, etc.`: Kotlin kennt keine Primitivtypen. `Int` ist daher eine vollwertige Klasse.
    var counter: Int = 1

    // Typen können weg gelassen werden, wobei hier `buffer` trotzdem den Typ `String` hat, da `intro` ein ebenfalls `String` ist.
    // Der Compiler hat die Fähigkeit, Typen implizit zu ermitteln, wo dies möglich ist. Generell sollte man diesem Mechanismus sparsam, da er ansonsten
    // Ein Bezeichner, der erstmal einem Typ zugeordnet ist, kann seinen Typ nicht mehr ändern. Hier bleibt alles beim Alten.
    // recht schnell unübersichtlicht wird.
    var buffer = intro

    // `Array` ist in Kotlin normale Collectionklassen mit eigenen Methoden und kein Primitivtyp.
    // `forEach` übernimmt z.B. einen Funktionslambda, der als Parameter `element` hat. Diese Funktion wird nun für jedes Element des Arrays ausgeführt.
    args.forEach { element ->
        buffer += "\n $counter: $element"   // Kotlin kennt Stringinterpolation.
        counter++
    }

    // `println` ist eine von einigen Funktionen die standardmäßig im Scope vorhanden sind.
    println(buffer)
}

/*
kotlin.*
kotlin.annotation.*
kotlin.collections.*
kotlin.comparisons.* (since 1.1)
kotlin.io.*
kotlin.ranges.*
kotlin.sequences.*
kotlin.text.*

*/