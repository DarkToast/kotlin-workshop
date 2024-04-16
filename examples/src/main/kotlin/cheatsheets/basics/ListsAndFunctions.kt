@file:Suppress("UNUSED_VARIABLE", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE", "UNUSED_VALUE")

package cheatsheets.basics

import java.util.Optional

fun main() {
    // Kotlin bringt seine eigene Collections-API mit.
    // Die Sprache unterscheidet zwischen immutable Collections, wie hier der immutable `List`
    val list: List<String> = listOf("blue", "green", "red", "1")

    // Immutable Listen können nicht verändert, aber für neue Listen verwendet werden. Das macht sie im Bereich
    // der parallelen Verarbeitung essenziell um z.B. race Conditions zu vermeiden.
    val sublist = list.subList(0, 2)    // Erzeugt eine neue Liste mit den ersten beiden Elementen von `list`
    val newList = list + sublist        // Mit dem `+`-Operator können zwei Listen zu einer neuen Liste konkateniert
                                        // werden.
    // Intern werden die Werte allerdings nicht kopiert, sondern nur deren Referenzen!


    // Der Gegenpart ist die mutable List. Eine neue Liste kann per "Hand" erzeugt werden, Kotlin bietet aber auch
    // hier Helferfunktionen an, welche mehr Komfortfunktionen bieten.
    val manualList: MutableList<String> = ArrayList()
    val mutableList: MutableList<String> = mutableListOf("blue", "green", "red")

    // Mutable List können direkt bearbeitet werde.
    mutableList.add("blue")
    mutableList.add("green")
    mutableList.add("red")
    mutableList.remove("blue")

    // Daneben gibt weitere Strukturen:
    // Die Map bietet eine klassische key-value Struktur, wobei der Key eineindeutig vergeben wird.
    val map: Map<String, String> = mapOf(Pair("foo", "FOO"), Pair("bar", "BAR"))
    // Die Map existiert wie die Liste in einer immutable und einer mutable-Form
    val mutableMap: Map<String, String> = mutableMapOf(Pair("foo", "FOO"), Pair("bar", "BAR"))

    val v1: String? = map["foo"]                // sucht anhand eines Keys einen Wert. Gibt immer einen nullable-Wert zurück.
    val e: Boolean = map.containsKey("bar")     // Einige Methoden bieten eine vorherige Abfrage an.

    // Das Set bietet das Kotlin Äquivalent einer endlichen Menge. Wie in einer mathematischen Menge, so k̈́önnen
    // Elemente auch hier nur einmal vorkommen. Dubletten werden ignoriert.
    val set: Set<String> = setOf("blue", "blue", "green", "red")
    val subset = set - "blue"
    val disjunction = set + setOf("yellow", "gray")

    // Sets gibt es ebenfalls als mutable-Variante.
    val mSet: MutableSet<String> = mutableSetOf("blue", "green", "red")
    mSet -= "blue"
    mSet += setOf("yellow", "gray")


    // Die Collections haben monadische Methoden, welche eine Tranformationsfunktion entgegennehmen. Einen so genannten
    // Lambda ausdruck.
    // Hier als Beispiel ein Filter, welcher aus der Liste
    //   listOf("blue", "green", "red", "1")
    // alle Elemente "blue" herausfiltert.
    var blueList = list.filter({ value: String -> value == "blue" })

    // Die runden Klammern können auch weggelassen werden.
    blueList = list.filter { value: String -> value == "blue" }

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
    println(contractList.find { c: Contract -> c.type == "Wasser" })        // Findet das erste Element mit dem Typ "Wasser".
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
