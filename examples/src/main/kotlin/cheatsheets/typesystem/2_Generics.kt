package cheatsheets.typesystem

import java.util.ArrayList

class `2_Generics` {

    /**
     * Kotlin unterstützt Generics, bzw. Typparameter
     */
    fun typeArgumentedTypes() {
        val stringList: MutableList<String> = mutableListOf()
        val intList: MutableList<Int> = mutableListOf()

        // Ein `MutableList<String>` ist nun ein anderer Gesamttyp als `MutableList<Int>`
        // stringList = intList;            // geht nicht
        // stringList.addAll(intList);      // geht auch nicht

        // WICHTIG: Typparameter unterliegen nicht der Polymorphie!
        var objectList: MutableList<Any> = ArrayList()
        // objectList = stringList          // auch das geht nicht
    }

    /**
     * Alle Collections in Kotlin haben Typparameter.
     * Untypsierte Listen gibt es in Kotlin nicht.
     * Möchte man eine Liste für alles, nimmt man eine List of `Any`
     */
    fun anyList(): String {
        val myList: MutableList<Any> = mutableListOf()
        myList.add("Hallo Welt")
        myList.add(42)
        myList.add(Any())
        return myList[0] as String // <-- is it a String???
    }

    /**
     * Neben konkreten Typen, stolpert man relativ schnell über das Wildcard *
     * Es wird dann eingesetzt, wenn der Typ egal ist. Also z.B. beim Serialisieren oder zum Aufrufen von `toString`.
     * Beim Lesen z.B. einer Liste kann man vom Typ `Any` ausgehen.
     *
     * Möchte man dagegen z.B. eine `MutableList<*>` verändern, so werden die entsprechenden Methoden den
     * Parametertyp `Nothing` aufweisen. Eine Änderung ist somit leider nicht möglich!
     */
    fun wildcard() {
        fun wildcardList(l: List<*>): MutableList<*> {
            return (l + listOf("Hallo Welt")).toMutableList()
        }

        // list kann man als `MutableList<Any>` beim Lesen
        val list: MutableList<*> = wildcardList(listOf(123))
        // list.add(123) //expecte type `Nothing`
    }

    /**
     * Typparameter lassen sich als festen Wert in eine Ableitung einarbeiten:
     * `IntegerList` ist fest auf den Typ `Int` festgelegt:
     */
    internal class IntegerList : ArrayList<Int>()

    fun useOfIntegerList() {
        val intList = IntegerList()
        intList.add(1)
        // intList.add("hallo");    <-- Won't work
    }

    /**
     * Generische Klassen zu nutzen ist eine Sache, eine eigene zu schreiben eine andere.
     *
     * Das `E` ist hier lediglich ein Platzhalter für den späteren Typ. Der Name des Typparameters.
     * Dieser Typparameter `E` wird dann vom Benutzer mit einem Typ gefüllt.
     *
     * Per Konvention werden diese Typparameter immer in upper case geschrieben.
     *
     * @param <E>
     */
    internal class ElementAdder<E> {
        fun addElement(list: MutableList<E>, element: E): List<E> {
            list.add(element)
            return list.toList()
        }
    }

    fun useOfElementAdder() {
        val stringAdder = ElementAdder<String>()
        val strList = stringAdder.addElement(ArrayList(), "hallo Welt")

        val doubleAdder = ElementAdder<Double>()
        val doubleList = doubleAdder.addElement(ArrayList(), 42.0)
    }

    /**
     * Typeparameter können auch direkt an Methoden angeheftet werden.
     */
    fun <E> addElement(element: E): List<E> {
        return listOf(element)
    }

    fun useTypedMethods() {
        // Expliziter Typparameter für die Methode:
        val stringList = addElement<String>("Hallo Welt")

        // Oft kann der Compiler den Typ aber auch implizit ermitteln:
        val intList = addElement(42)
    }


}