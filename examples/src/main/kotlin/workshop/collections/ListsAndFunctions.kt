package workshop.collections

import java.util.Optional

fun functions() {

    // Kotlin bringt seine eigene Colletions-API mit.
    // Die Sprache unterscheidet zwischen immutable Collections, wie hier der `List`
    val list: List<String> = listOf("blue", "green", "red")

    // und mutable Lists. Eine neue Liste kann per "Hand" erzeugt werden,
    // Kotlin bietet aber auch globale Helfermethoden an.
    val mutableList: MutableList<String> = ArrayList() // = mutableListOf("blue", "green", "red")
    mutableList.add("blue")
    mutableList.add("green")
    mutableList.add("red")

    // Danaben gibt auch viele andere Arten
    val map: Map<String, String> = mapOf(Pair("foo", "FOO"), Pair("bar", "BAR"))
    val mutableMap: Map<String, String> = mutableMapOf(Pair("foo", "FOO"), Pair("bar", "BAR"))
    val set: Set<String> = setOf("blue", "blue", "green", "red")

    // Die Collections haben, wie z.B. auch bei Java 8 Streams, monadische Methoden, welche einen Lambdaausdruck
    // entgegennehmen. Hier als Beispiel ein Filter:
    val blueList = list.filter({ value: String -> value == "blue" })

    // Die runden Klammern kann man auch weg lassen:
    val upperCaseList = list.map { value -> value.toIntOrNull() }

    // Lambdas kann man in Kotlin auch direkt als Funktionen einer Variable zuweisen.
    // Der Typ der FUnktion setzt sich dabei aus den Eingabe- und Ausgabetypen zusammen:
    val toUpperCase: (String) -> Int = { value -> Integer.parseInt(value) }
    val intList: List<Int> = list.map(toUpperCase)

    // Die Lambdaausdrücke und Funktionen sind auch mit dem Java 8 SDK kompatibel:
    val optional = Optional.of("Foobar")
    val upperCase = optional.map { it.toUpperCase() }
    val upperCase2 = optional.map(toUpperCase)
}
