@file:Suppress("UNUSED_VARIABLE", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE", "UNUSED_VALUE")

package material.basics

fun main() {
    val colorList: List<String> = listOf("blue", "green", "red")

    /**
     * Lambdas und Collections
     */
    // Generell lassen sich alle Liste mit Schleifen iterieren. Jedes Element kann dann separat betrachtet werden
    // und eine Operation auf diesem Element vollführt werden.
    for (s in colorList) {
        println("Farbe: $s")
    }

    // Die Collections bieten als weiteren Weg auch Methoden an, welche eine Transformationsfunktion
    // entgegennehmen. Einen sogenannten "Lambda"-Ausdruck:
    // Hier als Beispiel ein Filter, welcher aus der Liste
    //   listOf("blue", "green", "red")
    // alle Elemente "blue" herausfiltert und in eine neue Liste speichert.
    var blueList: List<String> = colorList.filter({ value: String -> value == "blue" })

    // Die runden Klammern können auch weggelassen werden:
    blueList = colorList.filter { value: String -> value == "blue" }

    // `{ value: String -> value == "blue" }` ist ein Lambdaausdruck, also eine anonyme Funktion, die wir `filter`
    // als Parameter übergeben. Das `value: String` vor dem `->` ist dabei der erste Parameter der Funktion.
    // Alles was nach dem `->` steht ist der Funktionskörper. Der Rückgabetyp der Funktion basiert auf dem Typ des
    // letzten Statements. Ein `return` ist nicht nötig und hier auch nicht erlaubt.
    // In diesem Beispiel wird der String `value` auf den Wert "blue" verglichen. Das Ergebnis ist ein `Boolean`.

    // Alternativ ein imperativer Ansatz, der dasselbe macht. Sehr grob gesagt ist der Unterschied wie folgt:
    // Beim imperativen Ansatz geben wir einer Funktion ein Objekt zur Manipulation.
    // Beim funktionalen Ansatz geben wir einem Objekt zur Manipulation für sich selbst.
    val mList = mutableListOf<String>()
    for (i in colorList) {
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

    // Filter alle Elemente mit dem Typ "Strom" aus.
    val stromContract: List<Contract> = contractList.filter { c: Contract -> c.type == "Strom" }

    // Findet das erste Element mit dem Typ "Wasser". Der Wert ist nullable, falls kein Element gefunden wurde
    val wasserContract: Contract? = contractList.find { c: Contract -> c.type == "Wasser" }

    // Transformiert alle Elemente der Liste in den entsprechenden Typ um. Also eine List<Contract> zu einer List<String>.
    val types: List<String> = contractList.map { c: Contract -> c.type }

    // Flatten ist eine Methode um verschachtelte Listen, also 2-dimensionale Listen in einem 1-dimensionale Liste
    // umzuwandeln. Das Beispiel erzeugt eine List<Int> mit (2, 3, 4, 5)
    val list3: List<List<Int>> = listOf(listOf(2, 3), listOf(4, 5))
    val flattenList: List<Int> = list3.flatten()

    // Bei einer List<Int> existiert die Methode `sum`. um alle Elemente zusammenzuaddieren.
    val intList = listOf(1, 2, 3, 4, 5, 6)
    val sum = intList.sum()

    // Die String-Elemente einer List<String> können sehr einfach konkateniert werden:
    val strList : List<String> = listOf("Hallo", "Welt", "!")
    val str: String = strList.joinToString(" ")

    // Der "lange", aber auch generische Weg ist per "reduce" um jede beliebige List auf einen Wert zu reduzieren.
    // In dem Beispiel wird der `reduce`-Methode ein Lambda mit zwei Parametern, beide vom Typ String, übergeben:
    strList.reduce { acc: String, value: String ->
        println("acc is $acc - value is $value")
        acc + value
    }

    // 'sortedBy' sortiert eine Liste neu anhand des Rückgabetyps des Lambdas. Wichtig ist, das dieser Typ das
    // Interface `Comparable` implementiert. String tut dies, wodurch eine lexikalische Sortierung entsteht.
    val sorted = colorList.sortedBy { value: String -> value }

    // `associateBy erzeugt aus einer Liste eine Map. Das Lambda muss dabei den Wert zurückgeben, welcher den Key
    // es entsprechenden Elements repräsentiert. In diesem Fall entsteht eine Map mit der Vertrags-ID als Key und
    // dem eigentlichen Vertrag als value.
    val map2: Map<Int, Contract> = contractList.associateBy { value: Contract -> value.id }

    // `all` übernimmt ebenfalls einem Funktionslambda, erwartet allerdings, dass die Funktion als Rückgabetyp ein `Boolean`
    // angibt. Gilt die Aussage des Lambdas für alle Elemente, so gibt `all` ebenfalls `true` zurück.
    // Somit bilden wir einen Allquantor auf die Liste.
    val aQuantor: Boolean = contractList.all { c: Contract -> c.type.lowercase() == "strom" }

    // `any` ist das Pendant zu `all` und bildet einen Existenzquantor. `any` gibt also `true` zurück, wenn es mindestens
    // ein Element in der Liste gibt, auf das das Lambda `true` zurückgibt,
    val eQuantor: Boolean = contractList.any { c: Contract -> c.type.lowercase() == "strom" }

    // `forEach` führt das übergebene Lambda einfach auf jedes Element aus. Der Rückgabewert ist dabei egal.
    contractList.forEach { c: Contract -> println(c.type) }

    /**
     * Lambdas als first class citizens:
     */
    // Lambdas kann man in Kotlin auch direkt einer Variable zuweisen und die Funktion als
    // "normalen" Wert benutzen. Also Funktionen als Parameter verwenden, Funktionen aber auch
    // als Rückgabewert benutzen. Dafür brauchen Funktionen Typen:
    //   + Name des Wertes
    //   |
    //   |         + Der Typ der Funktion. Gesprochen "String auf Int"
    //   |         | Dieser Typ definiert alle Funktionen, welche einen `String` in einen `Int` umwandeln können.
    //   |         |
    //   |         |                     + Die eigentliche Funktion hinter dem =. `value: String` ist dabei der
    //   |         |                     | erste Eingabeparameter. `value.length` definiert den Funktionskörper.
    //   |         |                     |
    val stringLength: (String) -> Int = { value: String -> value.length }

    // Der Wert `stringLength` kann dann z.B. für `map` verwendet werden.
    var lengthList: List<Int> = colorList.map(stringLength)

    // und ist äquivalent zu:
    lengthList = colorList.map { value: String -> value.length }


    // Als weiteres Beispiel für die Wiederverwendbarkeit von Lambdas:
    // Wir definieren ein Lambda und können ihn in drei Funktionen als
    // Parameter einsetzen:
    val isContractStrom: (Contract) -> Boolean = { c: Contract -> c.type == "Strom" }

    // Filter alle Stromverträge und gibt sie als Liste zurück
    val allStromContracts: List<Contract> = contractList.filter(isContractStrom)
    // Gibt den ersten Stromvertrag der Liste zurück.
    val firstStromContract: Contract = contractList.first(isContractStrom)

    // Gibt den letzten Stromvertrag der Liste zurück.
    val lastStromContract: Contract = contractList.last(isContractStrom)
}
