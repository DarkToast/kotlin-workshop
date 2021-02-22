package cheatsheets.fp

/**
 * In der funktionalen Programmierung
 */
// first class citizen
fun firstClass() {


}


data class Measurement(val type: String, val value: Int)
data class Temperature(val value: Int)

sealed class Season
object Spring : Season()
object Summer : Season()
object Autumn : Season()
object Winter : Season()

fun compositionFunction() {
    val x: (Int) -> Boolean = { i -> i > 0 }
    val y: (Int) -> Boolean = { i -> i < 0 }

    var z = x
    z = { _ -> false }

    z = { i -> x(i) || y(i) }   // composition


    // Einige Beispiele für einzelne Funktionen:
    // M -> T
    // T -> S
    // S -> Boolean
    val define: (measurement: Measurement) -> Temperature? = { m ->
        if (m.type == "t") Temperature(m.value) else null
    }

    val season: (temperature: Temperature) -> Season = { t ->
        when {
            t.value > 20 -> Summer
            t.value > 10 -> Autumn
            t.value > 5 -> Spring
            else -> Winter
        }
    }

    val isSummer: (season: Season) -> Boolean = { s -> s is Summer }


    // Einzeln aufgerufen erhalten wir das Ergebnis:
    val m = Measurement("t", 10)
    val t = define(m) ?: Temperature(0)
    val s = season(t)
    var result = isSummer(s)


    // Wir können aber auch durch geschicktes Kombinieren eine neue Funktion "on-the-fly" bauen:
    // Als Ergebnis ein M -> Boolean
    val isMeasurementInSummer: (Measurement) -> Boolean = { me ->
        isSummer(season(define(me) ?: Temperature(0)))
    }

    result = isMeasurementInSummer(m)


    // Nur jetzt bauen wir uns Kombinations-Funktionen:
    fun andFab(x: (Int) -> Boolean, y: (Int) -> Boolean): (Int) -> Boolean {
        return { i -> x(i) && y(i) }
    }

    fun orFab(x: (Int) -> Boolean, y: (Int) -> Boolean): (Int) -> Boolean {
        return { i -> x(i) || y(i) }
    }

    //  z = { i -> x(i) || y(i) }   // composition
    // Statt explizit Funktionen zu kombinieren, können wir uns auch einer Kombinations-"Factory" bedienen:
    z = orFab(x, y)
}

fun currying_schönfinkeln() {

    fun add(x: Int, y: Int) = x + y
    var z = add(1, 2)

    fun curry_add(x: Int): (Int) -> Int {
        return {y -> x + y}
    }

    val curry_add2: (Int) -> (Int) -> Int = { x -> { y -> x + y }}

    val onePlus = curry_add(1)
    z = onePlus(2)
}

