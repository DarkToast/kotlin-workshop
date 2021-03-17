package cheatsheets.fp

import java.util.Optional

/**
 * Der Begriff "Monade" taucht immer dann auf, wenn man auf Artikel über "moderne" funktionale Prorammierung stößt.
 * Dann bekommt man meist eine Menge Mathematik und Begriffe wie "Kategorientheorie" vorgeworfen.
 * Einfacher ist es, wenn man sich dem Begriff nicht von der Mathematik, sondern als Design-Pattern nähert.
 *
 * Dabei muss man immer im Kopf behalten, dass diese funktionalen Konstrukte erst mal nichts von
 * objektorientierten Konstrukten "wissen". So lassen sich einige Basispattern wesentlich einfacher mit einer Klasse
 * lösen
 */

// Motivation:
// ===========
// Eine rein funktionale Programmiersprache hat keine Seiteneffekte. Klingt theoretisch gut, ist aber praktisch nutzlos.
// Seiteneffekte wie jede Form von IO oder z.B. ein Fehler- oder ein Validierungshandling werden in diesen Sprachen
// mit Monaden erledigt.
// Eine Monade soll daher einen Seiteneffekt kapseln und den Effekt soweit "schieben" wie möglich. Dadurch soll
// das eigentliche Programm so wenig wie möglich tangiert werden.
// Beispiel:
//   Ein Parameter kann einen Wert darstellen, aber auch `null` sein. Aus dieser `nullable`-Eigenschaft folgert ein
//   Seiteneffekt der, nimmt man es genau, bei jedem Lesenden Zugriff eintritt. Beim Fall eines `null` muss der
//   Programmfluss unterbrochen werden. Der Seiteneffekt tritt ein.

// An diesem Beispiel wollen wir mal die Eigenschaften einer Monade ansehen:
//
// 1: Eine Monade ist erst mal ein Container, welcher Elemente eines Typs ummanteln kann. In unserem Fall den Wert, den
//    dessen potentielle `null`-Natur kapseln wollen.
//    In Kotlin haben wir das Elemente der Klassen und Generics um diese Anforderung abzubilden:
class Nullable1<T>



// 2: Eine Monade muss die "Einheitsfunktion" besitzen um aus einem Typ `T` die Monade `M<T>` zu erzeugen.
//    Kotlin macht es hier einfach: Das ist ein Konstruktor:
class Nullable2<T>(private val value: T?)

val value: String = "test"
val m2: Nullable2<String> = Nullable2(value)




// 3: Eine Monade muss den `Funktor`-Operator besitzen, sodass gilt:
//    funktor(M<T>, f: T -> U): M<U>
//    Wir brauchen also ein Ding, welches eine Monade `M<T>` nimmt und eine Funktion, um aus einem T ein U zu machen.
//    Als Ergebnis erhalten wir die Monade `M<U>`. In der freien Wildbahn kennt man diesen Funktor als `map`-Funktion.
//    Auch "leben" die Funktoren in objektefunktionalen Sprachen nicht in einem globalen Scope, sondern im Scope der Monade:
class Nullable3<T>(private val value: T?) {

    // Man sieht, dass hier der "Seiteneffekt" das erste mal eintritt. Sie wird aber weiterhin
    // in der Monade gekapselt. Nach Außen wirkt die Monade weiterhin als M<U>. Man nennt dies auch einen "circuit breaker".
    // Oder in Anlehnung an die quantenmechanische Unbestimmtheit: Eine Monade ist die Schrödingerkatze der Informatik.
    fun <U> map(f: (T) -> U?): Nullable3<U> = if(value != null) {
        Nullable3(f(value))
    } else {
        Nullable3(null)
    }
}

val m3: Nullable3<Boolean> = Nullable3(value)
    .map { s: String -> s.length }
    .map { len: Int -> null }           // Hier passiert der `null`-Fall. Nichtsdestotrotz kann man aber weiterhin Transformationen ausführen!
    .map { len: Int -> len == 3 }




// 4: Neben dem Funktor-Operator besitzt eine Monade einen `join`-Operator, sodass gilt:
//    join(M<M<T>>): M<T>
//    `join` flacht unsere Monade also ab. Man kennt die Funktion auch sehr oft als `flatten`, was ich sprechender finde:
class Nullable4<T>(val value: T?) {
    fun <U> map(f: (T) -> U?): Nullable4<U> = if(value != null) {
        Nullable4(f(value))
    } else {
        Nullable4(null)
    }
}

// Aufgrund der type erasure Natur der JVM Generics, kann die flatten Methode in
// Kotlin am einfachsten als Extension gebaut werden, da wir hier den genauen
// rekursiven Typaufbau erweitern können:
fun <U> Nullable4<Nullable4<U>>.flatten(): Nullable4<U> = this.value ?: Nullable4(null)


val m4: Nullable4<String> = Nullable4(value)    // Nullable4<String>
    .map { s: String -> Nullable4("s is $s") }  // Nullable4<Nullable4<String>
    .flatten()                                  // Nullable4<String>




// 5: Neben dem Funktor-Operator besitzt eine Monade einen `bind`-Operator, sodass gilt:
//    bind(M<T>, f: T -> M<U>): M<U>
//    Also ähnlich zum Funktor-Operator, nur dass unsere Transformationsfunktion eine Monade als Ergebnis hat.
//    Man kann diese Methode auch aus `map` mit `flatten` kombinieren. (s. m4), sodass man diese Kombination
//    dann als `bind`-Funktion im Namen `flatMap` vereint. Daher fehlt `flatten` auch oft, da man mit
//    `flatMap` die meisten Fälle abdecken kann.
class Nullable5<T>(val value: T?) {
    fun <U> map(f: (T) -> U?): Nullable5<U> = if(value != null) {
        Nullable5(f(value))
    } else {
        Nullable5(null)
    }

    fun <U> flatMap(f: (T) -> Nullable5<U>?): Nullable5<U> = if(value != null) {
        f(value) ?: Nullable5(null)
    } else {
        Nullable5(null)
    }
}

fun <U> Nullable5<Nullable5<U>>.flatten(): Nullable5<U> = this.value ?: Nullable5(null)

val m5: Nullable5<String> = Nullable5(value)        // Nullable5<String>
    .flatMap { s: String -> Nullable5("s is $s") }  // Nullable5<String>
    .flatMap { s: String -> null }                  // Nullable5<String>




// 6: Zu guter Letzt muss man auch irgendwie wieder an den Value ran kommen.
//    Hier wird es nun spannend, da wir nun den Seiteneffekt auflösen oder in die Kiste gucken, ob die Katze nun
//    tot ist oder noch lebt.
//    Generell gibt es hier keine formalen Methoden, da sie sehr oft Seiteneffektspezifisch sind. In unserem Fall
//    können wir einfach ein get() machen, welche uns ein nullable Ergebnis zurückliefert. Zumindest ein transformiertes
//    nullable. Oder wir geben die Möglichkeit eines default Values, welches zu guter Letzt die letzte
class Nullable<T>(val value: T?) {
    fun <U> map(f: (T) -> U?): Nullable<U> = if(value != null) {
        Nullable(f(value))
    } else {
        Nullable(null)
    }

    fun <U> flatMap(f: (T) -> Nullable<U>?): Nullable<U> = if(value != null) {
        f(value) ?: Nullable(null)
    } else {
        Nullable(null)
    }

    fun get(): T? = value

    fun getOrElse(default: T): T = value ?: default
}

fun <U> Nullable<Nullable<U>>.flatten(): Nullable<U> = this.value ?: Nullable(null)

val string: String = Nullable(value)                // Nullable<String>
    .flatMap { s: String -> Nullable("s is $s") }   // Nullable<String>
    .getOrElse("My default")


// Monadische Strukturen finden sich überall in der Kotlin, aber auch in der Java-API:
val opt: Int = Optional.of("Hallo Welt")
    .map { s -> s.length }
    .flatMap { len -> Optional.empty<Int>() }
    .orElse(42)

val list: String = listOf("Hallo", "Welt", ".", "Du", "bist", "so", "schön", ".")
    .flatMap { s -> s.asIterable() }
    .joinToString()
