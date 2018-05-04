package session1

/* Kotlin erlaubt stand alone functions, sodass wir die main-Methode als einzelne Funktion im der Datei definieren können.
   Schauen wir sie uns ein wenig genauer an, um ein wenig die Syntax von Kotlin zu verstehen.

   Zunächst fällt das `fun`-Wort auf. Damit werden alle Funktionen/Methoden in Kotlin eingeleitet. Warum das nötig ist,
   werden wir sehen, wenn wir uns um Rückgabetypen kümmern.

   Als nächstes folgt der Funktionsname `main` und die Argumentenliste. Hier fällt als erstes auf, dass in Kotlin
   zunächst der Variablenname und erst dann, mit `:` getrennt, der Typ geschrieben wird. Also `name: Type`. Generell
   wird in Kotlin alle Typen immer hinten angestellt.

   So auch der Rückgabetyp `Unit` der Funktion `main`. `Unit` ist in Kotlin das Äquivalent zu `void` in Java.
*/
fun main(args: Array<String>): Unit {

    // `val` definiert ein "immutable Value". Wie `final`-Variablen in Java.
    val intro: String = "Arguments: "

    // `var` definiert eine "mutable Variable". Generell gilt: So viel `val` wie möglich, so viel `var` wie nötig.
    // Ein Wort zu `Int, Double, FLoat, etc.`: Kotlin kennt keine Primitivtypen. `Int` ist eine vollwärtige Klasse.
    var counter: Int = 1

    // Typen können we gelassen werden. `buffer` hat aber trotzdem den Typ `String`, da `intro` ein `String` ist.
    // Typen werden also implizit vom Compiler ermittelt. Generell sollte man diesem Mechanismus sparsam, da er ansonsten
    // recht schnell unübersichtlicht wird.
    var buffer = intro

    // Arrays sind in Kotlin normale Klassen mit eigenen Methoden. `forEach` übernimmt einen Funktionslambda, der
    // als einen Parameter `arg` hat. Diese Funktion wird von Array nun für jedes Element ausgeführt.
    args.forEach { arg ->
        buffer += "\n $counter: $arg"   // Kotlin kennt Stringinterpolation.
        counter++
    }

    // `println` ist von einigen Funktionen die standardmäßig im Scope vorhanden sind.
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