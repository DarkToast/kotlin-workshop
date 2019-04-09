package oneday.oop

import session2.Account
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