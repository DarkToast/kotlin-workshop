@file:Suppress("UNUSED_VALUE", "UNUSED_VARIABLE", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE", "RedundantExplicitType",
    "KotlinConstantConditions", "CanBeVal"
)

package material.basics

import material.Customer

val customer = Customer()

fun main() {
    // Generell können "normale" Typen in Kotlin keine `null`-Referenz enthalten. Also einen Verweis auf ein nicht existentes Objekt.
    // Ein `notNullable = null` ist nicht möglich.
    var notNullable: String = "Hallo"

    // Generell sollte und muss eine Sprache aber mit der Nichtexistenz umgehen können um z.B. optionale Werte und noch fehlende Werte abbilden 
    // zu können. Dafür existieren die `nullable types`. Also dieselben Typen, die nun aber auch `null`-Referenzen enthalten können.
    // Ein als nullable deklarierter Typ wird mit einem `?` am Ende des Typnamens markiert.
    var nullable: String? = null

    // Eine direkt Zuweisung `notNullable = nullable` ist nicht möglich, da so `notNullable` ggf. ein `null` zugewiesen werden könnte.
    // Ein `nullable = notNullable` ist dagegen möglich. Kotlin erlaubt diese Zuweisung mit einem impliziten Typecast, da
    // es den Wertebereich des `nullable`-Typs nicht verletzt.
    // geht nicht:      notNullable = nullable
    // geht:            nullable = notNullable

    // Eine Zuweisung des nullable Typs zu einem not nullable Typs wird nur dann erlaubt, wenn ein expliziter `null`-Check
    // getätigt wurde. In diesem Statement, wird `nullable` überprüft und nur dann erlaubt der Kotlin Compiler die
    // Zuweisung und den impliziten Typecast.
    if (nullable != null) {
        notNullable = nullable
    }

    // Im folgenden Beispiel greifen wir auf ein Customer Objekt zurück. Alle Typen und Methoden sind hier grundlegend `nullable`.

    // Bei dieser Aneinanderreihung von nullable-Types bietet uns Kotlin als Alternative den `null check operator` `?.`.
    // Dieser Operator wirkt wie ein Kurzschluss. Würde `getContract()` `null` zurück geben, so würde der Aufruf
    // `getBaseFee` nicht mehr ausgeführt, sondern die komplette Expression würde zu `null` aufgelöst werden. Ein Fehler
    // in Form einer NullPointerException tritt nun nicht mehr auf.

    // Am Ende der Kette sehen wir einen weiteren Operator. Den DefaultValue-Operator `?:`. er hat in der Aufrufkette
    // folgende Bedeutung: Sollte irgendwo innerhalb der Kette ein `null` aufgetreten und der Ausdruck zu `null`
    // aufgelöst worden sein, so greift der `?:` Operator und gibt den Defaultwert (hier 0.0) zurück. Ist der Ausdruck
    // nicht `null`, so wird dieser zurückgegeben.

    // Ist also `getContract()` oder `getBaseFee()` `null`, so hat `baseFee` den Wert 0.0.
    // Ist keiner der Ausdrücke `null`, so hat `baseFee` den Wert, der `getBaseFee()` zurückgegeben hat.
    val baseFee: Double = customer.getContract()?.getBaseFee() ?: 0.0

    // Ein etwas destruktiverer Weg bietet der `!!`-Operator. Mit ihm kann man tatsächlich einen `nullable` direkt in
    // einen nicht nullable type umwandeln. Hier geht man aber aktiv die Gefahr einer NullPointerException ein!
    val baseFee2: Double = customer.getContract()!!.getBaseFee()!!
}
