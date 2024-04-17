@file:Suppress("UNUSED_VARIABLE", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE", "UNUSED_VALUE")

package cheatsheets.basics

import java.util.Optional

fun main() {
    /**
     * Collections
     */
    // Kotlin enthält eine eigene Collections-API.
    // Die Collections-Lib unterscheidet dabei zwischen immutable Collections, wie hier der immutable `List`
    // und mutable, also veränderliche, Liste.
    val colorList: List<String> = listOf("blue", "green", "red")

    // Immutable Listen können nicht verändert, aber für neue Listen verwendet werden. Das macht sie im Bereich
    // der parallelen Verarbeitung essenziell um z.B. race Conditions zu vermeiden.
    val sublist = colorList.subList(0, 2)    // Erzeugt eine neue Liste mit den ersten beiden Elementen von `list`
    val newList =
        colorList + sublist        // Mit dem `+`-Operator können zwei Listen zu einer neuen Liste konkateniert
    // werden.
    // Intern werden die Werte allerdings nicht kopiert, sondern nur deren Referenzen!


    // Der Gegenpart ist die mutable List. Eine neue Liste kann per "Hand" erzeugt werden, Kotlin bietet aber auch
    // hier Helferfunktionen an, welche mehr Komfortfunktionen bieten.
    val manualList: MutableList<String> = ArrayList()
    val mutableList: MutableList<String> = mutableListOf("yellow")

    // Mutable List können direkt bearbeitet werde. In diesem Beispiel wird der Liste drei neue Farben hinzugefügt
    // und eine Farbe wird wieder entfernt
    mutableList.add("blue")
    mutableList.add("green")
    mutableList.add("red")
    mutableList.remove("blue")
    // --> ["yellow", "green", "red"]

    // Daneben gibt es weitere Strukturen:
    // Die Map bietet eine klassische key-value Struktur, wobei der Key eineindeutig vergeben wird.
    val map: Map<String, String> = mapOf(Pair("foo", "FOO"), Pair("bar", "BAR"))

    // Die Map existiert wie die Liste in einer immutable und einer mutable-Form
    val mutableMap: Map<String, String> = mutableMapOf(Pair("foo", "FOO"), Pair("bar", "BAR"))

    val v1: String? = map["foo"]                // sucht anhand eines Keys einen Wert. Gibt immer einen nullable-Wert zurück.
    val e: Boolean = map.containsKey("bar")     // Einige Methoden bieten eine vorherige Abfrage an.

    // Das Set bietet das Kotlin Äquivalent einer endlichen Menge. Wie in einer mathematischen Menge, so können
    // Elemente auch hier nur einmal vorkommen. Dubletten werden ignoriert.
    val set: Set<String> = setOf("blue", "blue", "green", "red")
    val subset = set - "blue"
    val disjunction = set + setOf("yellow", "gray")

    // Sets gibt es ebenfalls als mutable-Variante.
    val mSet: MutableSet<String> = mutableSetOf("blue", "green", "red")
    mSet -= "blue"
    mSet += setOf("yellow", "gray")
}
