package workshop.collections

import java.util.Optional

fun main() {
    // Kotlin bringt seine eigene Colletions-API mit.
    // Die Sprache unterscheidet zwischen immutable Collections, wie hier der `List`
    val list: List<String> = listOf("blueblue", "green", "red", "1")

    // und mutable Lists. Eine neue Liste kann per "Hand" erzeugt werden,
    // Kotlin bietet aber auch globale Helfermethoden an.
    val mutableList: MutableList<String> = mutableListOf("blue", "green", "red")
    mutableList.add("blue")
    mutableList.add("green")
    mutableList.add("red")

    // Danaben gibt auch viele andere
    val map: Map<String, String> = mapOf(Pair("foo", "FOO"), Pair("bar", "BAR"))
    val mutableMap: Map<String, String> = mutableMapOf(Pair("foo", "FOO"), Pair("bar", "BAR"))
    val set: Set<String> = setOf("blue", "blue", "green", "red")


    // Die Collections haben, wie z.B. auch bei Java 8 Streams, monadische Methoden, welche einen Lambdaausdruck
    // entgegennehmen. Hier als Beispiel ein Filter:
    var blueList = list.filter({ value: String -> value == "blue" })

    // Die runden Klammern können auch weggelassen werden.
    blueList  = list.filter { value: String -> value == "blue" }

    // Alternativ ein imperativer Ansatz. Sehr grob gesagt ist der Unterschied wie folgt:
    // Beim imperativen Ansatz geben wir einer Funktion ein Objekt zur Manipulation.
    // Beim funktionalen Ansatz geben wir einem Objekt zur Manipulation für sich selbst.
    val mList = mutableListOf<String>()
    for (i in list) {
        if (i == "blue") {
            mList.add(i)
        }
    }

    // Ein weiteres Beispiel nun mit konkreten Fachklassen.
    // Wir haben einen Contract, welcher eine ID und einen Vertragstyp im Energiesektor widerspiegelt.
    // Damit werden einige Collectionmethoden gezeigt, die im Alltag häufig vorkommen.
    data class Contract(val id: Int, val type: String)
    val contractList: List<Contract> = listOf(
        Contract(1, "Strom"), Contract(2, "Gas"), Contract(3, "Wasser"), Contract(4, "Wasser")
    )

    println(contractList)                                                  // Gibt die gesamte Liste aus.
    println(contractList.filter { c: Contract -> c.type == "Strom" })      // Filter alle Elemente mit dem Typ "Strom" aus.
    println(contractList.find { c: Contract -> c.type == "Wasser"})        // Findet das erste Element mit dem Typ "Wasser".
    println(contractList.map { c: Contract -> c.type })                    // Transformiert alle Elemente der Liste in den
                                                                           // entsprechenden Typ um. Also eine List<Contract>
                                                                           // zu einer List<String>.

    // Flatten ist eine Methode um verschachtelte Listen, also 2-dimensionale Listen in einem 1-dimensionale Liste
    // umzuwandeln.
    val list3: List<List<Int>> = listOf(listOf(2, 3), listOf(4, 5))
    println(list3)
    println(list3.flatten())        // erzeugt eine List<Int> mit (2, 3, 4, 5)

    // Bei einer List<Int> existiert die Methode `sum` um alle Elemente zusammenzuaddieren.
    println(list3.flatten().sum())
    list3.flatten().reduce { acc: Int, value: Int ->
        println("acc is $acc - value is $value")
        acc + value
    }

    // 'sortedBy' sortiert eine Liste neu anhand des Rückgabetyps des Lambdas. Wichtig ist, das dieser Typ das
    // Interface `Comparable` implementiert. String tut dies, wodurch eine lexikalische Sortierung entsteht.
    val sorted = list.sortedBy { value: String -> value + "foobar" }

    // `associateBy erzeugt aus einer Liste eine Map. Das Lambda muss dabei den Wert zurückgeben, welcher den Key
    // es entsprechenden Elements representiert. In diesem Fall entsteht eine Map mit der Vertrags-ID als Key und
    // dem eigentlichen Vertrag als value.
    val map2: Map<Int, Contract> = contractList.associateBy { value: Contract -> value.id }

    // Die runden Klammern kann man auch weg lassen:
    val upperCaseList: List<Int?> = list.map { value: String -> value.toIntOrNull() }

    // Lambdas kann man in Kotlin auch direkt als Funktionen einer Variable zuweisen.
    // Der Typ der Funktion setzt sich dabei aus den Eingabe- und Ausgabetypen zusammen:
    val toUpperCase: (String) -> Int = { value -> Integer.parseInt(value) }
    val intList: List<Int> = list.map(toUpperCase)

    // Die Lambdaausdrücke und Funktionen sind auch mit dem Java 8 SDK kompatibel:
    val optional = Optional.of("Foobar")
    val upperCase = optional.map { it.uppercase() }
    val upperCase2 = optional.map(toUpperCase)
}
