/**
 * Themen:
 *  - Die KotlinFile
 *  - Alles sind Objekte
 *  - String interpolation
 *  - value equality
 */

// Kotlin ist in ein Packagesystem eingebunden. Hier gelten die Regeln: Pro Package, ein Verzeichnis.
// mit 1 .. n *.kt Dateien.
package material.basics

/*
  Kotlindateien können mehrere Elemente, wie Variablen, Funktionen, Klassen etc. enthalten und sind nicht an einen Typ gebunden:
  Die Elemente sollten aber immer einen Bezug zueinander haben. Der umschließende Dateiname sollte daher einen, alle Elemente beschreibenden,
  Namen besitzen.
*/
val x = "Hello world"

fun helloWorld() {
    println("Hello world")
}

class Foo() {
}

class Bar

// Da Kotlin vornehmlich auf der JVM beheimatet ist, ist der zentrale Einstiegspunkt die, aus Java bekannte, `main`-Funktion.
fun main() {
    
    // Generell ist in Kotlin alles ein Objekt. Hier handelt es sich um ein `Int`-Typ.  Primitive Typen wie aus C, Java, etc. bekannt, 
    // gibt es für den Kotlincompiler nicht.
    val x: Int = 42
    val y: Int = 42

    // So hat `Int` z.B. auch eine `toString`- oder z.B. `plus`-Methode
    x.toString()
    x.plus(5)

    // Um Werte zu vergleichen, bietet Kotlin den `==`-Operator als Wertevergleich an.
    println(x == y)

    // Das gilt für alle Objekte. Hier der `List`-Typ. "Unter der Haube" wird dabei die Methode `equals` aufgerufen.
    val list1: List<String> = listOf("Hallo", "Welt")
    val list2: List<String> = listOf("Hallo", "Welt")

    // Für einen Vergleich auf Objekt-Referenzen wurde in Kotlin der `===`-Operator eingeführt.
    println(list1 == list2) // true
    println(list1 === list2) // false

    // Kotlin bietet einen modernen Umgang mit Strings mit String-Interperloation, also dem direkten Nutzen von Values und Variablen in String:
    println("Klein aber fein: Stringinterpolation:  $x")

    // Und die Möglichkeit der Mulit-Line Strings. Als Beispiel kann hier mit sehr wenig Aufwand ein Json-Payload erstellt und mit String-Interpolation
    // mit Werten gefüllt werden.
    println(
        """
        | {
        |   "value1": "Und das ist ein",
        |   "value2": "schöner multi line String."
        |   "value3": "$x"
        | }
    """.trimMargin("|")
    )
}
