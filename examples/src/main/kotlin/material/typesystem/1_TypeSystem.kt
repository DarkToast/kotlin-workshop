package cheatsheets.typesystem

fun typeSystem() {
    val foo: String = ""        // non nullable
    val bar: String? = null     // nullable

    // Ein spezielles object, was als "leerer" Rückgabetyp für Funktionen dient.
    // Analog zum Java oder C `void`
    val unit: Unit

    // `Any` ist der Supertype aller Klassen. Auch Javas Object leitet von Any ab.
    var any: Any = 12
    any = "Hallo Welt"
    any = 1.234
    any = Object()

    // Nothing ist das Gegenpol zu `Any`. Es ist der gemeinsame Subtyp aller Klassen.
    // Nothing kann nicht istantiiert werden, es gibt auch keine Konstanten davon.
    // Es dient lediglich als
    val no: Nothing

    // Beispiel: Eine Methode die lediglich eine Exception wirft, die man aber in jedem Kontext einsetzen möchte:
    // Kotlins ToDo() arbeitet so.
    fun MyToDo(msg: String): Nothing = throw RuntimeException("ToDo: $msg")

    // `someCalculation` möchte ein `Int` zurück geben. Wir geben ein `Nothing` zurück, was ja von `Int` "abgeleitet" ist.
    // Der Unterschied, bzw. die Einsatzszenarien von `Unit` und `Nothing` ->
    //
    // `Unit` zeigt das Ausbleiben eines Rückgabetyps an
    // `Nothing` zeigt einen Rückgabetyp an, für den es niemals einen Wert gibt.
    fun someCalculation(): Int = MyToDo("still missing")


    // Im funktionellen Bereich gibt es noch `Function`, welches den Obertyp aller Lamdas und Funktionen darstellt.
    // Der Typparameter beschreibt den Rückgabewert.
    var f: Function<String> = { x: String, y: String -> "$x bar"}
    f = { "bar"}
    f = { i: Int -> "$i"}
}