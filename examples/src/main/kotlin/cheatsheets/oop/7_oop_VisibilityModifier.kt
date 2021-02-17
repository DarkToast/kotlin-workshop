package cheatsheets.oop

/**
 * Der Klassenkonstruktor ermöglicht direkt die Definition von Klassenattributen:
 * - onlyConstructor ist ein normales Konstruktorargument, welches kein Attribut wird und nur bei der
 *   Initialisierung lesbar ist.
 * - Das `val publicAttribute` erhält durch das `val` Keyword den Zustand eines `public` Attributs.
 * - `private val privateAttribute` ist ebenfalls ein Attribut, allerdings `private`.
 */
class VisibilityClass(onlyConstructor: Int, val publicAttribute: Int, private val privateAttribute: Int) {

    /**
     * `private` vals sind nur innerhalb des Klassenscopes sichtbar.
     */
    private val privateValue: Int = 1

    /**
     * `protected` ist innerhalb des Klassenscopes und im Scope abgeleiteter Elemente sichtbar.
     */
    protected val protectedValue: Int = 2

    /**
     * `internal` ist innerhalb eines Kotlinmoduls sichtbar. Nicht aber von anderen Modulen.
     * Ein Modul kann dabei ein
     *  - IntelliJ Modul
     *  - Maven Modul
     *  - Gradle source set
     *  - Summe von Dateien innerhalb eines Ant Tasks.
     *  sein.
     */
    internal val internalValue: Int = 0

    /**
     * `public` ist von allen Elementen des Programms aus sichtbar. Generell muss man den Modifier in Kotlin nicht
     * angeben, da `public` der Standardmodifier von allen Elementen.
     */
    public val publicValue: Int = 3

    private val bar = PrivateClass()
}

/**
 * Neben Methoden und Attributen können auch Klassen einen Sichtbarkeitsmodifikator besitzen.
 *
 * `private` classes sind nur innerhalb einer Kotlindatei verwendbar. Referenzen auf Instanzen müssen ebenfalls
 * `private` sein und dürfen den Scope der Datei nicht verlassen.
 */
private class PrivateClass

/**
 * Internal classes sind nur innerhalb des Kotlin-Moduls sichtbar. Hiermit lassen sich gute Verbindungen unter
 * Modulen realisiere, da nicht alle Klassen public sein müssen, sondern im Zweifel nur eine handvoll Interfaces und
 * Factoryklassen.
 */
internal class InternalClass {
    private val privateRef = PrivateClass()
}

/**
 * Public Klassen sind von allen Stellen aus sichtbar. Auch von nutzenden Modulen.
 */
public class PublicClass

/**
 * Alle Klassen in Kotlin sind standardmäßig nicht mehr ableitbar. Möchten wir es dennoch erlauben, so muss die
 * Klasse als `open` deklariert werden. Wichtig ist hierbei, dass es sich NICHT um einen Sichtbarkeitsmodifikator
 * handelt. ein `private open class` ist also möglich.
 */
open class OpenClass(private val init: Int) {

    /**
     * Was für Klassen gilt, gilt auch für Methoden. Möchten wir in einer abgeleiteten Klasse eine Methode überladen,
     * so muss die Ursprungsmethode zunächst als `open` deklariert werden.
     */
    open fun createX(): Int = 4 + init

    /**
     * Diese Methode lässt sich in abgeleiteten Klassen nicht mehr überladen.
     */
    fun createY(): Int = 5 + init
}

/**
 * Diese Klasse leitet nun von `OpenClass` ab. Sie kann dabei aber nicht mehr weiter abgeleitet werden,
 * da sie selbst nicht `open` ist.
 */
class InheritedClass : OpenClass(5) {

    /**
     * Da `createX` open ist, können wir sie auch überladen.
     * Generell muss bei einer Implementierung oder einer Überladung immer das
     * `override` Keyword vorangestellt werden.
     */
    override fun createX(): Int = 4711
}
