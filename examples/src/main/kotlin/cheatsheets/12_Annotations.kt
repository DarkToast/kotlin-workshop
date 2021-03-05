package cheatsheets

import com.fasterxml.jackson.annotation.JsonIgnore

class Annotations {
    @Deprecated(message = "since version 0.9", replaceWith = ReplaceWith("cheatsheets.newOne"))
    fun deprecated(): Int = 3

    @Throws(Exception::class)
    fun newOne(): Int = 4

    @Synchronized
    fun anotherOne(): Int = 6

    @Transient
    @Volatile
    var transientVolatile: Boolean = true

    /**
     * delegate – a field storing a delegated property
     * field – a field generated for a property
     * file – a class containing top-level functions and properties defined in that file
     * get/set – the property getter/setter
     * param – a constructor parameter
     * property – the Kotlin’s property, it is not accessible from Java code
     * receiver – the receiver parameter of an extension function or property
     */
    @get:JsonIgnore
    @set:JsonIgnore
    var value: Int = 5

    @JvmField
    val jvmPublic = 6

    companion object {
        @JvmStatic
        val jvmStaticField = 7

        val kotlinStaticField = 8
    }

    // Inline functions werden vom Compiler en block in den aufrufenden Code kopiert.
    // Die Funktion als solches verschwindet also im Bytecode.
    inline fun inlineFun(s: (Int) -> String): String = s(42)
}

// Inline class sind noch experimentel.
// Sie representieren auf Codeebene einen expliziten Typ und einen darunterliegenden "Primitivtyp."
// Auf Bytecodeebene wird dann lediglich der Primitivtyp benutzt.
inline class Radius(val r: Double)

fun main() {
    val r: Radius = Radius(5.5)
    val d: Double = 5.5
    println(r)
}