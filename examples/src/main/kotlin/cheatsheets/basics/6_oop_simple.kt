package cheatsheets.basics

/**
 * Themen:
 *  - simple Klassen
 *  - Konstruktoren
 *  - init-Block
 *  - Simple Generics
 */

// Die simpelste Klasse. Ohne Attribute oder Konstruktor.
class Simple

// Der Standardkonstruktor wird direkt hinter dem Namen angelegt. In diesem Fall mit dem Parameter `x: Int`.
class Verbose constructor(x: Int) {
}


// Die Klasse `Account` besitzt einen Standardkonstruktur mit drei Parametern. Das `constructor`-Wort wurde hier weg gelassen.
// - Ein `val` definiert automatisch ein `public` Klassenattribut.
// - `private val` erzeugt ein `private` Attribut.
// - Ohne `val` handelt es sich lediglich um einen Konstruktorparameter, dessen Scope nur innerhalb des `init`-Blocks und
//   der Variableninitialisierung liegt.
class Account(val name: String, password: String, private val salt: String = "#salt#") {

    // Ein klassisches Attribut der Klasse.
    private val passwordHash: String

    // Der `init`-Block ersetzt den Konstruktorbody.
    init {
        passwordHash = password + salt
    }

    // Eine normale Funktion der Klasse.
    fun authenticate(password: String): Boolean {
        return this.passwordHash == password + salt
    }
}

fun main(args: Array<String>) {
    val s: Simple = Simple() // Neuen Objekten muss in Kotlin kein `new` vorangestellt werden. Man ruft quasi
    // "direkt" die Konstruktorfunktion auf.

    val account1 = Account("Max Mustermann", "s3cr3t", "mySalt")
    val account2 = Account(
        "Hans Dampf",
        "s3kr3t"
    ) // --> Mit den default Argumenten können wir mit einem Konstruktor, viele Fälle unterscheiden.

    // Wir haben nun Zugriff auf `name` und `authenticate`
    println(account1.name)
    println(account1.authenticate("s3cr3t"))

    // Kotlin unterstützt parametrisierte Typen (aka Generics, Templates).
    // Typparameter werden in spitze Klammern geschrieben. Mehr dazu in einer späteren Session.
    val list: List<String> = listOf("Hallo", "Welt")
}
