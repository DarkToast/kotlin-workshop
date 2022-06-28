// Paketdekleration. Muss äquvivalent zur Verzeichnisstruktur sein
package workshop.kbasics

// Import statements um Klassen, aber auch statische Elemente
// zu importieren
import java.security.SecureRandom
import kotlin.math.E

// Die Mainmethode als zentraler Programmeinstieg ohne Klassenbezug.
// Der Rückgabetyp `Unit` ist äquivalent zu `void` aus anderen Sprachen
fun main(args: Array<String>) {
    println(SecureRandom().nextInt() + E)
}

// Paketfunktion ohne Klassenbezug. Werden über den Paketnamen referenziert.
fun myStaticFunction(x: Int, y: Int): Int {
    return x + y
}

// Pro Datei lassen sich beliebig viele Klassen definieren
class MyFirstClass()

class MySecondClass
