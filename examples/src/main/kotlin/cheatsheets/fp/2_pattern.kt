package cheatsheets.fp

import cheatsheets.Autumn
import cheatsheets.Measurement
import cheatsheets.Season
import cheatsheets.Spring
import cheatsheets.Summer
import cheatsheets.Temperature
import cheatsheets.Winter
import java.lang.IllegalArgumentException

/**
 * In der funktionalen Programmierung lassen sich durch die Prinzipien der first-class-citizen und der higher-order
 * functions auch eine beliebige Kombination erzeugen.
 */
fun functionComposition() {

    // Als Beispiel zwei Funktionen, welche jeweils einen Zahlenwert überprüfen.
    // x prüft auf größer 0, y auf kleiner 0
    val x: (Int) -> Boolean = { i -> i > 0 }
    val y: (Int) -> Boolean = { i -> i < 0 }

    // Ohne neue Überprüfungen wollen wir nun einen Vergleich auf ungleich 0 machen.
    // Wir können nun eine Funktion `z` erzeugen, welche `x` und `y` kombiniert:
    var z: (Int) -> Boolean = { i -> x(i) || y(i) }   // composition


    // Ein etwas fachlicheres Beispiel.
    // Aus einem Measurement (M) lässt sich ggf. eine Temperatur(T) erzeugen.
    // Aus einer Temperatur(T) lässt sich auf eine Season(S) schließen.
    // Und anhand einer Season(S) aus Aussage ob es sich um den Sommer handelt.
    // Kurzum
    // M -> T;  T -> S;  S -> Boolean

    // Eine Funktion (M) -> T
    val define: (measurement: Measurement) -> Temperature = { m ->
        if (m.type == "t") Temperature(m.value) else throw IllegalArgumentException("Only temperatures!")
    }

    // Eine Funktion (T) -> S
    val season: (temperature: Temperature) -> Season = { t ->
        when {
            t.value > 20 -> Summer
            t.value > 10 -> Autumn
            t.value > 5 -> Spring
            else -> Winter
        }
    }

    // Eine Funktion (S) -> Boolean
    val isSummer: (season: Season) -> Boolean = { s -> s is Summer }


    // Einzeln aufgerufen erhalten wir das Ergebnis:
    val m = Measurement("t", 10)
    val t = define(m) ?: Temperature(0)
    val s = season(t)
    var result = isSummer(s)


    // Wir können aber auch durch geschicktes Kombinieren eine neue Funktion "on-the-fly" bauen:
    // Als Ergebnis ein M -> Boolean
    var isMeasurementInSummer: (Measurement) -> Boolean = { me ->
        isSummer(season(define(me) ?: Temperature(0)))
    }
    result = isMeasurementInSummer(m)


    // Der Nachteil ist: `isMeasurementInSummer` ist nicht Nebeneffektsfrei. Alle kombinierten Funktionen kommen
    // per Closureausdrücke mit in den Scope. Ggf. kann es hier zu Unsauberkeiten oder Abhängigkeiten kommen,
    // die wir verhindern wollen.
    // Abhilfe schaffen und hier die Higher-Order-Funktionen, mit denen wir uns "Kombinations-Fabrics" bauen können:

    // `andFab` kombinert z.B. das Ergebnis zweier Funktionen (Int) -> Boolean per AND
    fun andFab(x: (Int) -> Boolean, y: (Int) -> Boolean): (Int) -> Boolean {
        return { i -> x(i) && y(i) }
    }

    // `orFab` kombiniert zwei Funktionen (Int) -> Boolean per OR
    fun orFab(x: (Int) -> Boolean, y: (Int) -> Boolean): (Int) -> Boolean {
        return { i -> x(i) || y(i) }
    }

    // Beispiel von oben: `z` lässt sich so ohne closures aufbauen:
    z = orFab(x, y)

    // `combine` kombiniert zwei Funktionen so, dass das Ergebnis der ersten Funktion, die Eingabe der zweiten
    // Funktion datstellt. Etwas "generischer" und mit drei Typen:
    fun <X, Y, Z> combine(a: (X) -> Y, b: (Y) -> Z): (X) -> Z {
        return { value -> b(a(value)) }
    }

    // Mit `combine` lässt sich `isMeasurementInSummer` auch ohne Closurevariablen "bauen"
    isMeasurementInSummer = combine(combine(define, season), isSummer)
}

/**
 * Ein von Haskell Brooks Curry, aber auch Moses Schönfinkel entwickeltes mathematisches Konzept um aus
 * einer Funktion mehrerer Veränderlicher eine Sequenz von Funktionen mit lediglich einem Parameter zu erschaffen.
 *
 * Die Umwandlung wird `currying`, bzw. das Ergebnis eine `curried`-function. Man kann natürlich auch den Herrn
 * Schönfinkel zur Benennung nehmen, wodurch wir `schönfinkeln`, bzw. eine `geschönfinkelte`-Funktion erhalten.
 */
fun currying_schönfinkeln() {

    // Beispiel: Die Funktion `add` hat zwei Parameter um beide zu addieren:
    fun add(x: Int, y: Int) = x + y
    var z = add(1, 2)

    // Eine ge"curriete" - Funkion nimmt nun nur den ersten Parameter `x` und gibt als weitere Sequenz eine Funktion
    // zurück, die `y` annimmt und als Ergebnis die Summe daraus:
    fun curry_add(x: Int): (Int) -> Int {
        return { y -> x + y }
    }
    z = curry_add(1)(2)

    // In `Kurzform` als reine Funktionsausdrücke:
    val curry_add_short: (Int) -> (Int) -> Int = { x -> { y -> x + y } }

    val onePlus = curry_add(1)
    z = onePlus(2)

    // Eignet sich gut um Funktionen zu konfigurieren, aber auch Wiederzuverwenden:

    // Beispiel: `environment` schleift sich durch das gesamte Programm durch um z.B. den Zielhost für IO Operationen
    // zu ermitteln.
    data class Environment(val host: String)
    val prodEnvironment = Environment("farfar.away.prod.host:9010")
    val testEnvironment = Environment("localhost:9010")

    // Ein funktionaler, naiver Ansatz ist, das Environment immer mitzugeben und mit in die Berechnung einzubinden
    val getMeasurement_1: (environment: Environment, deviceId: String) -> Measurement? = { e, d ->
        val url = "${e.host}/$d"
        Measurement("humidity", 6059)
    }
    getMeasurement_1(prodEnvironment, "1234")

    // Ein funktionaler Ansatz mit einer "Konfigurationsfunktion"
    val getMeasurementFab = { env: Environment -> { deviceId: String -> null } }

    // Mit dieser Funktion können wir nun unser `getMeasurement` für die weitere Verwendung konfigurieren.
    // Natürlich ist es in einer objektorientierten Umgebung eine Kapslung in einer Klasse wesentlich effizienter, aber
    // ein "Bauen" und "konfigurieren" von Funktionen eröffnet einem eine weitere große Schublade im Werkzeugkasten.
    val getMeasurement = getMeasurementFab(prodEnvironment)

    getMeasurement("1234")
}

