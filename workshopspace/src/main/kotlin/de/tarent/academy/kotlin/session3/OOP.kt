package de.tarent.academy.kotlin.session3

import com.sun.javafx.collections.SortableList
import java.util.*
import java.util.logging.Logger

interface Arithmetic {
    fun add(a: Int, b: Int): Int

    fun subtract(a: Int, b: Int): Int
}

abstract class ExtendedArithmetic(val x: Int): Arithmetic {

    abstract fun multiply(a: Int, b: Int): Int

    abstract fun divide(a: Int, b: Int): Double

    abstract val basicInt: Int

    override fun add(a: Int, b: Int): Int = a + b + basicInt

    final override fun subtract(a: Int, b: Int): Int = a - b

}

abstract class ExtendedExtendedArithmetic(x: Int): ExtendedArithmetic(x) {
    override fun add(a: Int, b: Int): Int = a + b
}


open class ConcreteArithmetic(override val basicInt: Int): ExtendedArithmetic(42) {
    override fun multiply(a: Int, b: Int): Int {
        LOGGER("Multipling $a and $b")
        return a * b
    }

    override fun divide(a: Int, b: Int): Double = a.toDouble() / b

    companion object {
        private val LOGGER = { message: String ->
            println("INFO: $message")
        }
    }
}

class ConcreteConcreteArithmetic: ConcreteArithmetic(42) {

}

object IAmStatic {
    fun generateInt(): Int = 42
}

fun iUseAStatic() {
    println(IAmStatic.generateInt())
}



class Foo() {
    private val privateVal: Int = 1
    protected val protectedVal: Int = 1
    public val publicVal: Int = 1

    /**
     * Ein Modul kann dabei ein
     *  - IntelliJ Modul
     *  - Maven Modul
     *  - Gradle source set
     *  - Summe von Dateien innerhalb eines Ant Tasks.
     */
    internal val internalVal = 4

}

class Car {

    var passenges: Int = 0
        set(value) {
            if(value < 0 || value > 5) {
                throw IllegalArgumentException("")
            } else {
                field = value
            }
        }
}

fun main(args: Array<String>) {
    val car = Car()

    println(car.passenges)
    car.passenges = 6


    fun isVerifiedEmail(email: Email): Boolean {
        return when(email) {
            is UnverifiedEmail -> false
            is VerifiedEmail -> true
        }
    }

}























































