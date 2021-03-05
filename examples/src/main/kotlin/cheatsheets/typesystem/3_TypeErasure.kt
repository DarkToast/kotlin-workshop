package cheatsheets.typesystem


/**
 * Kotlin erzeugt in der Hauptsache JVM Bytecode, bzw. ist daran gebunden-
 * Die JVM speichert Typparameter allerdings nicht mit im Bytecode. Sie sind lediglich zur Compilezeit bekannt.
 * Dieses Prinzip nennt man "type erasure"
 */
class `3_TypeErasure` {

    // Z.B. lassen sich Methoden mit verschiedenen Typen überladen, aber nicht mit verschiedenenen Typparametern.
    fun first(l: List<String>): String = TODO()
    // fun first(l: List<Int>): String = TODO()

    // Ein when kann nicht den eigentlichen Typparameter einer `List of Any` erfragen:
//    fun map(list: List<Any>) = when(list) {
//        is List<String> -> println("String")
//        else -> println("Something else")
//    }

    // Ein Artefakt aus der Java-Welt sind die `Class`-Parameter um auch zur Laufzeit Typinformationen zu behalten:
    fun <T> javaStyle(l: List<T>, clazz: Class<T>) {
        when (clazz.name) {
            "int" -> l.forEach { println((it as Int) * 2) }
            else -> println("No Int list")
        }
    }

    // Kotlin hat hier eine Zusammenführung dieses Patterns in einen Ausdruck.
    // Mit `reified` lassen sich Typparameter zusammen mit dem Klassenobjekt übergeben.
    // Diese Funktionen müssen aber inline sein, da sie direkt in den Bytecode kopiert werden.
    // Bei vielen Aufrufen haben wir hier also eine vergrößerung des Programmartefakts.
    inline fun <reified T> kotlinStyle(l: List<T>) {
        when (T::class) {
            Int::class -> l.forEach { println((it as Int) * 2) }
            else -> println("No Int list")
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val te = `3_TypeErasure`()
            te.javaStyle(listOf(2, 4), Int::class.java)
            te.kotlinStyle(listOf(2, 4))
        }
    }
}