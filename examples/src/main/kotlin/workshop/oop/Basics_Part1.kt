@file:Suppress("UNUSED_VARIABLE", "unused", "UNUSED_PARAMETER")

import java.time.LocalDateTime

/**
 * Eine Klasse ist immer eine Abstraktion von realen, konkreten Objekten. Konkret heißt hierbei nicht unbedingt
 * in einem physikalischen Sinne, sondern Elemente und Begriffe aus der umzusetztenden Fachdomäne.
 *
 * Beispiele: Kunde, Vertrag, Farbe, Adresse, Hersteller, Koordinate, etc
 */
class Farbe

/**
 * Objekte sind konkrete Ausprägungen einer Klasse. Also zum Beispiel die Kundin "Frauke Musterfrau"
 * oder die Farbe "blau". In Kotlin definieren wir ein "value" mit einem entsprechenden Namen, welches
 * den Typ der Klasse hat. Also anstatt z.B. Int oder String nehmen wir hier "Kunde" und "Farbe".
 *
 * Als konkreten Wert rufen wir die Klassen mit ihrem Namen auf. Dieses erzeugt uns ein Objekt der Klasse
 * und weist es dem value zu.
 *
 * Ein Wort zur Terminologie und zur Einordnung bei der Entwicklung:
 * * Eine Klasse ist ein abstraktes Template oder Blaupause, welche beim Programmstart geladen wird, aber z.B. nur
 *   geringen Speicher verbraucht.
 * * `Farbe()` erzeugt ein neues Objekt der Klasse und allokiert den Speicher in der Größe der intern liegenden
 *   Attribute. Das Objekt befinden sich als Speicherallokation im Heap der JVM.
 * * `blau` ist ein Value und dieser Wert referenziert das Objekt im Heap.
 *   +------------+              +-----------------+
 *   | << blau >> |              | << 0x1234567 >> |
 *   | 0x1234567  |  ----------> |  Farbe-0815     |
 *   +------------+              +-----------------+
 */
val blau: Farbe = Farbe()
val gruen: Farbe = Farbe()


/**
 * Das obere Beispiel hat nun nur ein Problem. Wie können wir Objekte unterscheiden? Wo ist das Etwas, was ein Objekte
 * konkret werden lässt? Hier kommen die Attribute ins Spiel, welche eine Klasse mit Leben füllt. Ähnlich zu einem
 * klassischen Struct z.B. in C lassen sich so Werte gruppieren.
 *
 * Klassen gehen hier aber noch wesentlich weiter und beinhalten neben Daten auch ein eigenes Verhalten durch Methoden.
 * Methoden greifen auf Attribute zu und können Aussagen über den State eines Objekte geben oder den State verändern.
 */
class Kunde(
    var kundennummer: String,
    var vorname: String,
    var nachname: String,
    var registered: LocalDateTime
) {
    /**
     * Eine Methode, welche den internen State eines Objekts ändern.
     */
    fun namensAenderung(vorname: String, nachname: String) {
        this.vorname = vorname
        this.nachname = nachname
    }

    /**
     * Eine Methode, welche eine Aussage über den State eines Objekt tätigt.
     */
    fun registeredThisYear(): Boolean {
        return registered.year == LocalDateTime.now().year
    }
}

/**
 * Die hier erzeugten Objekte haben nun konkrete Ausprägungen durch ihre Attribute.
 *
 * Auf jedes Attribut kann hier auch zugegriffen werden. Da wir die Attribute als `var` deklariert haben,
 * können wir auch Schreibend darauf zugreifen.
 */
val max: Kunde = Kunde("00001", "Max", "Mustermann", LocalDateTime.now().minusYears(2))
val frauke: Kunde = Kunde("00002", "Max", "Mustermann", LocalDateTime.now())

fun access() {
    println(max.vorname)
    println(max.nachname)
    println(max.kundennummer)
    println(max.registered)

    max.kundennummer = "123456"
}


/**
 * Zu Constructors:
 * In Kotlin ist der Konstruktor ein wenig versteckter als z.B. in Java oder C++. In den geschweiften Klammern befinden
 * sich die Konstruktorargumente. Also die Werte, welche zum Erzeugen eines Objekts benötigt werden.
 */
class Name(val name: String) {
    init {
        if (name.length > 10) {
            throw RuntimeException("Name must not greater than 10 characters")
        }
    }
}

fun constructor() {
    val max = Name("Max")
    val fraukeLaraChristiane = Name("FraukeLaraChristiane")  // dieses Objekt wird nicht erzeugt.
    // Der Constructor schmeißt eine Exception
}

/**
 * Class Composition beschreibt das Verhalten eine Klasse aus weiteren Klassen zusammenzusetzen.
 * Das untere Beispiel beschreibt eine Schiffsreise. Diese ist zusammengesetzt aus:
 * - einen Schiff, was einen Namen hat.
 * - Einen Start als Koordinate
 * - Einem Ziel als Koordinate
 * - Eine Liste von Containern
 * - Einen Captain und zwei Listen von Personen
 *
 * Hier ist schön der Unterschied zwischen Class und Object zu sehen. Die Class Schiffsreise benutzt weitere Classes,
 * wie `Schiff`, `Koordinate`, `Container` und `Person`. `Koordinate` z.B. in zwei Fällen. Dahinter stehen während der
 * Laufzeit des Programms natürlich Referenzen auf Objects der entsprechenden Class, welche unterschiedliche Daten
 * hat. Die Koordinate `start` wird selbstverständlich andere Daten haben als `ziel`.
 */

class Koordinate(val longitude: Double, val latitude: Double)
class Container(val volumen: Int, breite: Long, hoehe: Long)
class Person(
    val vorname: String,
    val nachname: String,
)

class Schiff(val name: String)

class Schiffsreise(
    val schiff: Schiff,
    val start: Koordinate,
    val ziel: Koordinate,
    val container: List<Container>,
    val captain: Person,
    val offiziere: List<Person>,
    val mannschaft: List<Person>
)
