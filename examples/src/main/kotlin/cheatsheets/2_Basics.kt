/**
 * Themen:
 *  - Die KotlinFile
 *  - Alles sind Objekte
 *  - String interpolation
 *  - value equality
 */

// Kotlin ist, wie Java, in ein Packagesystem eingebunden. Hier gelten die Regeln von Java. Pro Package, ein Verzeichnis
// mit 1 .. n *.kt Dateien.

package cheatsheets

/*
  Kotlindateien sind nicht an Klassen gebunden, sondern sind seht frei in ihrem Inhalt.

  Generell können ohne Klassen Werte definiert werden. Diese gelangen dann in einen globalen Scope. Daher: Vorsicht mit
  solchen Konstrukten.
*/
val x = "Hello world"

fun helloWorld() {
    println("Hello world")
}

// Pro Kotlindatei können beliebig viele Klassen definiert werden. Da Klassen recht simpel erstellt werden können,
// wird man sich beim Umstieg auf Kotlin schnell auf dieses System einlassen. Die Datei sollte dann besser einen
// generischeren Namen besitzen.
class Foo() {

}

class Bar

fun main(args: Array<String>) {
    // Generell ist in Kotlin alles ein Objekt. Hier handelt es sich um ein `Int`-Typ.
    val x: Int = 42
    val y: Int = 42

    // So hat `Int` z.B. auch eine `toString`-Methode
    x.toString()

    // Da in Kotlin alles ein Objekt ist, hat man kurzerhand den `==`-Operator als `value-Equality` definiert, sodass
    // dieser Ausdruck `true` zurück gibt,
    println(x == y)

    // Das gilt für alle Objekte. Hier der `List`-Typ. Wichtig ist selbstverstänlich die Implementierung von `equals`.
    val list1: List<String> = listOf("Hallo", "Welt")
    val list2: List<String> = listOf("Hallo", "Welt")

    // Für einen Referenzvergleich wurde in Kotlin der `===`-Operator eingeführt.
    println(list1 == list2) // true
    println(list1 === list2) // false

    println("Klein aber fein: Stringinterpolation:  $x")

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
