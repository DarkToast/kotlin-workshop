package session3

import session2.Employee
import session2.FirstName
import session2.LastName
import session2.Salary

/**
 * Eine "normale" Klasse haben wir bereits kennen gerlent:
 */
class EmployeeFactory(val defaultSalary: Salary) {

    fun create(firstName: FirstName, lastName: LastName): Employee {
        return Employee(firstName, lastName, defaultSalary)
    }
}

/**
 * Kotlin kennt neben Klassen, Interfaces. Hier definieren wir uns ein `Arithmetic` interface,
 * welches zwei Methode definiert:
 */
interface Arithmetic {
    fun add(a: Int, b: Int): Int

    fun subtract(a: Int, b: Int): Int
}

/**
 * Abstrakte Klassen sind ein Zwischenschritt zwischen Interfaces und konkreten Klassen und werden mit dem
 * Wort `abstract` vor `class` definiert.
 *
 * Diese abstrakte Klasse implementiert das Interface `Arithmetic`. Dabei wird hinter dem Klassenname und einem `:`
 * das Interface oder die abzuleitende Klasse gesetzt.
 */
abstract class ExtendedArithmetic: Arithmetic {

    // Neue abstrakte Methode werden, anders als im Interface, mit einem `abstract` modifier versehen:
    abstract fun multiply(a: Int, b: Int): Int

    abstract fun divide(a: Int, b: Int): Double

    // Neben Methoden, lassen sich auch Felder definieren, die später mit einem Wert erfüllt werden müssen
    abstract val basicInt: Int

    // Implementierende oder abgeleitetete Methode müssen mit einem `override` versehen werden.
    // Nebenbei wird hier der "abstrakte" Wert `basicInt` benutzt.
    override fun add(a: Int, b: Int): Int = a + b + basicInt

    // Implementierungen, aber auch Klassen, die nicht weiter abgeleitet werden soll, können mit `final`
    // versehen werden.
    final override fun subtract(a: Int, b: Int): Int = a - b
}

/**
 * Statische Elemente werden anders als zum Beispiel in Java nicht mit Klassen vermischt.
 * Stattdessen gibt es in Kotlin das Konzept des `object`. Ein `object` kann Werte und Funktionen definieren,
 * hat aber keinen Konstruktor.
 */
object IAmStatic {
    fun generateInt(): Int = 42
}

// Aufgerufen werden Objectmethoden direkt mit dem Objectnamen:
fun iUseAStatic() {
    println(IAmStatic.generateInt())
}

/**
 * `ConcreteArithmetic` leitet `ExtendedArithmetic` ab. Da es sich hier um eine Klasse handelt,
 * müssen wir in Kotlin den Konstruktor von `ExtendedArithmetic` aufrufen. Daher die `()`.
 */
class ConcreteArithmetic: ExtendedArithmetic() {

    // Hier "implementieren" wir das Feld `basicInt`.
    override val basicInt: Int = 42

    override fun multiply(a: Int, b: Int): Int {
        LOGGER("Multiply $a with $b")
        return a * b
    }

    override fun divide(a: Int, b: Int): Double {
        LOGGER("Multiply $a with $b")
        return a / b.toDouble()
    }

    override fun add(a: Int, b: Int): Int {
        LOGGER("Add $a and $b")
        return super.add(a, b)
    }

    private fun format(prefix: String): String {
        return prefix + helloWorld
    }

    companion object {

        private val helloWorld: String = "Hallo Welt"

        val LOGGER = { message: String ->
            println("Log: $message")
        }
    }
}