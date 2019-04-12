package oneday.oop

import cheatsheets.Account
import cheatsheets.Arithmetic
import java.io.Serializable

// Eine Klasse namens `Simple'
// Der Standardkonstruktor wird direkt hinter dem Namen angelegt. In diesem Fall mit dem Parameter `x: Int`.
class Simple(x: Int) {
}

// Die Klasse `Account` besitzt einen Standardkonstruktur mit drei Parametern.
// - Ein `val` definiert automatisch ein `public` Klassenattribut.
// - `private val` erzeugt ein `privates` Attribut.
// - Ohne `val` handelt es sich lediglich um einen Konstruktorparameter, dessen Scope nur innerhalb des `init`-Blocks und
//   der Variableninitialisierung liegt.
// Zusätzlich implementiert die Klasse das Interface `Serializable`

class Account(val name: String, password: String, private val salt: String = "#salt#"): Serializable {

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

    companion object IAmStatic {
        private val generateInt: Int = 42
    }
}

fun Klassen() {
    // In Kotlin liefert die Klasse direkt ihre Konsturktormethode - kein `new` ist erforderlich
    val account1 = Account("Max Mustermann", "s3cr3t", "mySalt")

    // Per Standardargumente erhalten wir "überladene Konstruktoren" on the fly.
    val account2 = Account("Hans Dampf", "s3kr3t")

    // Wir haben nun Zugriff auf `name` und `authenticate`,
    // nicht aber auf `salt` und `passwordHash`
    println(account1.name)
    println(account1.authenticate("s3cr3t"))
}

// Static gibt es in Kotlin nicht. Stattdessen existiert ein statisches `object`
object IAmStatic {
    fun generateInt(): Int = 42
}

/*
 * Kotlin kennt neben Klassen, Interfaces.
 */
interface Arithmetic {
    fun add(a: Int, b: Int): Int

    fun subtract(a: Int, b: Int): Int
}

class MyArithmetic: Arithmetic {
    // Implementierende oder abgeleitetete Methode müssen mit einem `override` versehen werden.
    override fun add(a: Int, b: Int): Int = a + b

    override fun subtract(a: Int, b: Int): Int = a - b
}


class MultipleConstructors(val value: String) {

    constructor(value: Int): this(value.toString()) {
    }
}











