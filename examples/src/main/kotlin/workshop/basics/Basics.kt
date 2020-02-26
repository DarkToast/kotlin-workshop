package workshop.basics

fun basics() {
    // Immutable `value` - Erst der Name, dann der Typ!
    val intro: String = "Arguments: "

    // Mutable `variable` - Typenangaben können weg gelassen werden.
    var counter = 1
    counter += 1

    // eingebaute Methoden aus dem `global scope`
    println(counter)

    // Alles ist ein Objekt - Double, Float, Long, Int, Short, Byte
    counter.toString()

    // Typen können parametrisiert sein -> Generics.
    // Hier eine Liste
    val list1: List<String> = listOf("Hallo", "Welt")
    val list2: List<String> = listOf("Hallo", "Welt")

    // == macht einen value-equality Vergleich
    // === einen Referenzverleich
    println(list1 == list2)     // true
    println(list1 === list2)    // false

    // String können interpoliert werden
    println("Klein aber fein: Stringinterpolation:  $counter")

    // Auch mehrzeilig
    println("""
        | {
        |   "value1": "Und das ist ein",
        |   "value2": "schöner multi line String."
        |   "value3": "$counter"
        | }
    """.trimMargin("|"))

    // Methoden haben ebenfalls das Muster "erst der Name, dann der Typ". Der Rückgabetyp steht immer hinten
    // Methoden können verschachtelt (Methode in einer Methode) werden.
    fun helloWorld(world: String): String {
        return "Hello $world"
    }

    // Methoden können default parameter haben und per named parameter aufgerufen werden
    fun add(x: Int, y: Int = 2): Int {
        return x + y
    }

    add(4)
    add(y = 3, x = 1)


    // IF Conditions sind ähnlich zu anderen Sprachen
    if(counter == 42 && list1.isEmpty()) {
        println("Die Antwort")
    } else {
        println("Was anderes")
    }

    // Allerdings lassen sie sich auch als Expression schreiben
    // `theAnswer` ist eine der beiden Antworten
    val theAnswer: String = if(counter == 42) {
        "Die Antwort"
    } else {
        "Was anderes"
    }

    // Oder in kurz:
    val theAnswer2: String = if(counter == 42) "Die Antwort" else "Was anderes"
}

// Die Hauptmethode ist `main` und steht alleine.
fun main (args: Array<String>) {
    println("My first kotlin application")
}