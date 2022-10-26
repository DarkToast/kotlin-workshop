// Paketdekleration. Muss äquvivalent zur Verzeichnisstruktur sein
package workshop.kbasics

// Import statements um Klassen, aber auch statische Elemente
// zu importieren
import java.security.SecureRandom
import kotlin.math.E

/*
Kotlin bietet über seine kotlin-stdlib eine Reihe an Standardpaketen an mit der Kotlin erst vollständig wird.
Unter anderem:
* kotlin.collections    - Collections, Data structures
* kotlin.io             - IO operationen für Dateien und Streams
* kotlin.math           - Mathematische Funktionen und Konstanten
* kotlin.random         - Kotlin bases random generator
* kotlin.streams        - Interoperabilität mit Java 8 Streams
* kotlin.time           - Time und Date API

Btw: Das JRE bietet uns bereits eine Menge an Standard Javapaketen, die wir benutzen können.
Solange wir aber mit Kotlin arbeiten, sollte die kotlin-stdlib vorzuziehen sein. Unter anderem:
* java.util         - Collections, Data structures,
* java.io           - Dateioperationen
* java.math         - Arithmetic multiprecision
* java.net          - networking operations / sockets, DNS
* java.nio          - Non blocking I/O framework
* java.sql          - Basic database access through JDBC
* java.time         - Time and date API
* java.security     - cryptographic APO
*/


// Die Mainmethode als zentraler Programmeinstieg ohne Klassenbezug.
// Der Rückgabetyp `Unit` ist äquivalent zu `void` aus anderen Sprachen
fun main() {
    println(SecureRandom().nextInt() + E)
}

// Paketfunktion ohne Klassenbezug. Werden über den Paketnamen referenziert.
fun myStaticFunction(x: Int, y: Int): Int {
    return x + y
}

// Pro Datei lassen sich beliebig viele Klassen definieren
class MyFirstClass()

class MySecondClass


val one: Int = 1 // Int
val threeBillion: Long = 3000000000 // Long
val oneLong:Long = 1L // Long
val oneByte: Byte = 1

val b: UByte = 1u  // UByte, expected type provided
val s: UShort = 1u // UShort, expected type provided
val l: ULong = 1u  // ULong, expected type provided

val bool: Boolean = true
val aChar: Char = 'a'

val str: String = "abcd 123"
val arr: Array<Int> = arrayOf(1, 2, 3, 4, 5)