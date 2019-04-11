package cheatsheets

class Car {

    /**
     * Kotlinattribute sind anders als z.B. in Java nicht nur einfache Variablen, sondern bringen im Kontext der
     * Klasse automatisch get- und set-Accessor mit. Beim Zugriff von außen auf das Attribut werden dann die
     * Accessor-Methoden aufgerufen. Dieses Konzept kennt man z.b. von C#.
     *
     * Um diese Accessormethoden zu überschreiben, um z.B. Validierungen einzubauen, wird direkt hinter dem
     * Attribut mit `get()` und `set(value)` die passenden Methoden definiert:
     */
    var passengers: Int = 0
        get() {
            val maxPassengers = 5
            return if(field > maxPassengers) maxPassengers else field
        }
        set(value) {
            if(value < 0 || value > 5) {
                throw IllegalArgumentException("")
            } else {
                field = value
            }
        }
}

/**
 * Wird nun per `car.passengers = 6` auf das Attribut geschrieben, so wird der überladende Accessor aufgerufen, der
 * wiederum eine `IllegalArgumentException` wirft.
 */
fun main(args: Array<String>) {
    val car = Car()
    car.passengers = 6
}
