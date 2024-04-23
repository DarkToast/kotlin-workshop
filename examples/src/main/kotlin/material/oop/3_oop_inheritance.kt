@file:Suppress("USELESS_IS_CHECK", "unused", "RedundantExplicitType", "CanBeVal")

package material.oop

/**
 * Eine "normale" Klasse haben wir bereits kennen gelernt:
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
 * das Interface oder die abzuleitende Klasse gesetzt. Möchte man mehrere Interfaces implementieren, so werden diese nach
 * dem `:`, kommasepariert aufgelistet.
 */
abstract class ExtendedArithmetic : Arithmetic {

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
class ConcreteArithmetic : ExtendedArithmetic() {

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
        // Greift die private Funktion `LOGGER` zu.
        LOGGER("Add $a and $b")
        return super.add(a, b)
    }

    private fun getTheBasicInteger(): String {
        return basicInt.toString()
    }

    // Um Kotlinklassen den Zugriff auf statische Elemente zu gewähren, existiert das Konzept des `companion object`s
    // Dieses `object` wird direkt in der Klasse definiert und kann somit auf private Elemente der umgebenen Klasse
    // zugreifen, bzw. die Klasse auf private Elemente des `objects`.
    companion object {
        private val LOGGER = { message: String ->
            println("Log: $message")
        }

        fun printInformation(arithmetic: ConcreteArithmetic) {
            // Greift auf die private Methode `getTheBasicInteger` zu.
            println("The basic int is: ${arithmetic.getTheBasicInteger()}")
        }
    }
}


/*************************
 * Beispiel ANIMAL
 *************************/


/**
 * Als Beispiel für generelle Ableitungsregeln und Polymorphismus soll uns das altbekannte
 * Tierbeispiel dienen.
 *
 * Als Obertyp definieren wir das `Animal`. Da wir niemals ein konkretes `Animal` sehen werden,
 * definieren wir diesen Typ `abstract`. Er kann also nicht instantiiert werden.
 *
 * Neben dem abstrakten Typ definieren wir auch eine abstrakte Methode, lediglich mit der
 * Methodensignatur. Also Name, Parameter und Rückgabetyp aber kein Methodenbody.
 * Hier wird das Prinzip und die Mächtigkeit des Polymorphismus langsam sichtbar.
 * Alle von `Animal` abgeleiteten Klassen müssen diese Methode auf ihre Weise "implementieren".
 */
abstract class Animal(val name: String, val height: Int) {
    abstract fun move(): String
}

/**
 * `Bird` ist nun unsere nächste Ebene in der Klassenhierarchie. `Bird` leitet von Animal ab
 * und erbt alle Elemente von `Animal`. Also `name` und `height`. Da beide Werte Konstruktor-
 * argumente sind, müssen wir sie bei der Ableitung setzten. `Bird` "schleift" die Werte einfach
 * aus dem eigenen Konstruktor durch.
 *
 * Neben der eigentlichen Ableitung ist auch `Bird` abstrakt, da wir in unserer Hierarchie diesen
 * Typ noch weiter verfeinern wollen. Neben den konkreten Werten `name` und `height` über den Konstruktor
 * definiert Bird hier noch eine weitere abstrakte Methode und einen abstrakten Wert für alle Vögel.
 *
 * Als letztes implementiert `Bird` die abstrakte Methode `move` von `Animal`. Diese Implementierung gilt
 * für alle abgeleiteten Vögel. (Wir klammern mal Hühner oder den Vogel-Strauss aus)
 */
abstract class Bird(name: String, height: Int) : Animal(name, height) {
    abstract val wingspan: Int
    abstract fun tweet(): String

    override fun move(): String = "fly!"
}

/**
 * `Raven` ist nun die erste, konkrete Klasse, welche direkt von `Bird` ableitet. Hier laufen
 * das erste Mal alle Fäden zusammen.
 * - Wir haben den `name` und `height` von `Animal`, welche wir auch hier über den Konstruktor
 *   "durchschleifen".
 * - Die Methode `move` von `Animal` ist bereits von `Bird` implementiert worden. `Raven` erbt die
 *   Implementierung.
 * - `Bird` verlangt, dass ein Wert für `wingspan` "implementiert" wird. Dieser wird hier über per
 *   Konstruktorargument gesetzt. Man beachte das `override`. Btw: Das ist ein anderer Weg gemeinsame
 *   Werte neben den Konstruktorargumenten in der Basisklasse in einer Hierachie zu etablieren. Vorteil
 *   ist hier, dass wir die Werte nicht "durchschleifen" müssen.
 * - Bleibt noch die Implementierung von `tweet`, welche hier mit "Krah! Krah!" erfolgt.
 *
 */
class Raven(name: String, height: Int, override val wingspan: Int) : Bird(name, height) {
    override fun tweet(): String = "Krah! Krah!"
}

/**
 * Der Wellensittich ist analog zum `Raven` ein konkreter Typ, welcher von `Bird` ableitet.
 * `Budgie` hat aber ein paar andere Implementierungswege als `Raven`:
 * - `wingspan` wird hier nicht per Konstruktorargument gesetzt, sondern mit einem festen Wert implementiert.
 * - `height` aus dem ursprünglichen `Animal` wird auch hier fest mit dem Wert 18 gesetzt.
 * - `name` wird analog per Konstruktorargument "durchgeschleift"
 * - `tweet` wird analog fest implementiert.
 */
class Budgie(name: String) : Bird(name, 18) {
    override val wingspan: Int = 25

    override fun tweet(): String = "piep!"
}

/**
 * Mit `Dog` haben wir eine weitere konkrete Klasse, welche nun aber direkt von `Animal` ableitet.
 * Wie die Werte von `name` und `height` gesetzt werden haben wir nun bereits weiter oben mehrmals gesehen.
 *
 * `Dog` definiert für sich einen weiteren Wert `chipped`. `move` wird hier direkt implementiert.
 */
class Dog(name: String, height: Int, val chipped: Boolean) : Animal(name, height) {
    override fun move(): String = "run"
}

/**
 * `Cat` ist eine weitere konkrete Klasse, welche ebenfalls von `Animal` ableitet.
 *
 * Auch `Cat` definiert für sich einen weiteren Wert `wild`. `move` wird ebenfalls direkt implementiert.
 */
class Cat(name: String, height: Int, val wild: Boolean) : Animal(name, height) {
    override fun move(): String = "sneak"
}


fun main() {
    // Ein Laufzeitbeispiel:
    //
    // Zunächst definieren wir vier Objekte. Jeweils ein Rabe, ein Wellensittich,
    // ein Hund und eine Katze.
    val korax: Raven = Raven(name = "Korax", height = 60, wingspan = 120)
    val friedolin: Budgie = Budgie(name = "Friedolin")

    val wuffi: Dog = Dog(name = "Wuffi", height = 50, chipped = true)
    val mauzi: Cat = Cat(name = "Mautzi", height = 32, wild = false)

    // Schauen wir uns nun an, wie die Vererbung funktioniert:
    // Am besten wird die main-Methode ausgeführt, um es besser sehen zu können.
    // Je nachdem wie die Methoden `move` und `tweet` implementiert werden, wird ein
    // unterschiedliches Verhalten ausgeführt:
    println("${korax.name} is ${korax.height}cm height and has a wingspan of ${korax.wingspan}cm.")
    println("${korax.name} can ${korax.move()} and when it tweet its sounds like '${korax.tweet()}'.")
    println()
    println("${friedolin.name} is ${friedolin.height}cm height and has a wingspan of ${friedolin.wingspan}cm.")
    println("${friedolin.name} can ${friedolin.move()} and when it tweet its sounds like '${friedolin.tweet()}'.")
    println()

    // Die obere Benutzung schaut schon nach einer Vereinheitlichung aus. Wir brauchen noch eine Methode, die
    // mit `Raven`, wie `Budgie` klarkommt und die entsprechenden Methoden aufruft. Hier kommen nun die
    // abstrakten Typen zum Tragen. Der erste kleinste gemeinsame Typ von `Raven` und `Budgie`, welche über die Attribute
    // `name`, `height` und `wingspan` und die Methoden `move` und `tweet` verfügt, ist die Klasse `Bird`.
    // Hier sehen wir die erste Benutzung von Polymorphismus:
    // Der Parameter `bird` ist nur noch vom Typ `Bird`. Die konkrete Ausprägung des übergebenen Objekts wird
    // verschleiert, abstrahiert. Der Methode `printBirdBehaviour` reicht das aber, da hier die Details keine
    // Relevanz besitzt. Nichtsdestotrotz reagiert aber das konkrete Objekte, welche hinter der Referenz `bird` sitzt
    // mit den Methoden so wie es sie implementiert. Ein Aufruf mit `korax` und `friedolin` führt also zu einem
    // unterschiedlichen Verhalten und demselben Ergebnis wie weiter oben.
    fun printBirdBehaviour(bird: Bird) {
        println("Polymorph bird '${bird.name}' is ${bird.height}cm height and has a wingspan of ${bird.wingspan}cm.")
        println("Polymorph bird '${bird.name}' can ${bird.move()} and when it tweet its sounds like '${bird.tweet()}'.")
        println()
    }

    printBirdBehaviour(korax)
    printBirdBehaviour(friedolin)

    // Zum Verständnis zum Polymorphismus, Abstraktionen und Konkretisierungen: Die hier gezeigte Hierarchie hat drei
    // Schichten (Animal -- Bird -- Raven/Budgie) für Vögel und zwei Schichten (Animal -- Dog/Cat) für Hunde und Katzen.
    // Wir können nun z.B. den `Budgie` `friedolin` auf drei Arten betrachten:
    var budgie: Budgie = friedolin      // Als konkreten Typ `Budgie`
    var bird: Bird = friedolin          // Als abstrakten Typ `Bird`
    var animal: Animal = friedolin      // Und als noch abstrakteren Typ `Animal`

    // Wichtig ist nun, welcher Typ unsere Referenz hat und welche Sicht wir auf das darunter liegende Objekt haben.
    budgie.wingspan     // Ein `Budgie` hat eine `wingspan` und die Methode `move`
    budgie.move()

    bird.wingspan       // Ein `Bird` hat ebenfalls eine `wingspan` und die Methode `move`. Wir können aber keine
    bird.move()         // Aussage mehr darüber treffen, ob wir ein `Raven` oder ein `Budgie` sind.

    animal.height       // Ein `Animal` besitzt `wingspan` nicht, also können wir auf diesen Wert nicht zugreifen, auch
    animal.move()       // wenn das darunter liegende Objekt diesen Wert besitzt. Wir sehen diesen Wert einfach nicht,
    // da wir uns hier nur allgemein für `Animals` interessieren.

    // Die Referenz auf `Animal` ermöglicht daher auch, einen `Dog`, wie einen `Raven` gleichermaßen zu betrachten,
    // da beide Klassen auch ein `Animal` sind:
    animal = wuffi      // Hier wird auch klar, warum wir von `friedolin` nicht die `wingspan` sehen konnten. Der
    // Compiler schützt hier die Referenz vor einem ungültigen Zugriff, da z.B. `Dog` diesen Wert
    // nicht besitzt.

    // Mächtig wird diese Abstraktion vor allem, wenn wir mit Listen von verschiedenen Objekten arbeiten wollen:
    // Die Liste arbeiten lediglich mit dem abstrakten Typ, interessiert sich aber eben auch nur für die gemeinsamen
    // Eigenschaften. Durch den Polymorphismus wird aber je nach Objekt ein anderes Verhalten ausgeführt.
    val animalList: List<Animal> = listOf(wuffi, mauzi, friedolin, korax)
    animalList.forEach { a: Animal -> println("Abstract animal '${a.name}' can ${a.move()}.") }


    // Sollten wir aber mal von einem abstrakten Typ wieder auf den konkreten Typ schließen wollen,
    // so bietet uns Kotlin hier typsichere Abfragen an. Je nach Ergebnisbranch bekommen wir auch einen
    // automatischen Typecast:
    println()
    when (animal) {
        is Dog -> println("Animal is a Dog and it is ${if (animal.chipped) "chipped" else "not chipped"}")
        is Bird -> println("Animal is a Bird and has a wingspan of ${animal.wingspan}")
        else -> println("Aninal is something else and has the name ${animal.name}")
    }

    // Alternativ per if
    if (animal is Dog) {
        println("Animal is a Dog and it is ${if (animal.chipped) "chipped" else "not chipped"}")
    }


    // Es stellt sich nun die Frage: Wann nimmt man den konkreten Typ und wann macht es Sinn den abstrakten Typ benutzen?
    // Generell kann man die Frage mit zwei Antworten belegen:
    // - Wenn ich mehrere Objekte unterschiedlichen Typs zusammen behandeln will, so macht es Sinn den kleinsten gemeinsamen
    //   Typ zu benutzen. Bei einer Liste von `Raven` und `Budgies` würde sich eine List von `Birds` anbieten, da es
    //   der kleinste gemeinsame Typ ist. (List<Bird>)
    // - Konkrete Typen sollten immer die erste Wahl sein. Möchte ich zum Beispiel einzelne Objekte unterscheiden, weil
    //   ich sie fachliche anders behandeln will, kann ich hier das Typsystem für eben solche Fachunterscheidungen
    //   benutzen. Selbst wenn ich zwei Instanzen von `Bird` besitze, möchte ich gewisse Logiken einzig Objekten von `
    //   `Dog` vorbehalten. Das Typsystem
    // Die Entscheidung muss aber auch keine Einbahnstraße sein. Mit den oben gezeigten
    fun justHandleDogs(dog: Dog): Unit = println("Dog is chipped: ${dog.chipped}")

    justHandleDogs(wuffi)  // Das funktioniert. Ein Aufruf mit `justHandleDogs(bird)` würde dagegen scheitern.
    // Das Typsystem zwingt uns hier also zu einer fachlichen Fallunterscheidung bereits zur
    // Compilezeit. Einer der großen Stärken objektorientierter Sprachen.

    // Beispiel für eine Fallunterscheidung innerhalb der Liste. Der Compiler erlaubt uns keinen direkten Aufruf der
    // Listenelemente auf `justHandleDogs`, sondern zwingt uns dazu die Elemente erst zu filtern um nur die korrekten
    // Objekte als Paramter für die Methode zu benutzen
    animalList
        .filterIsInstance<Dog>()
        .forEach { dog: Dog -> justHandleDogs(dog) }

    // Als Gegenbeispiel das anti-pattern mit dem abstrakten Typ. Hier verschieben wir die Fallunterscheidungen auf einen
    // späteren Zeitpunkt. `justHandleDogs2` kapselt damit nicht den korrekten fachlichen Ablauf, sondern erlaubt den
    // Aufruf mit fachlich falschen Typen. Dies erhöht die Komplexität und erschwert die Lesbarkeit.
    fun justHandleDogs2(dog: Animal) {
        if (dog is Dog) {
            println("Dog is chipped: ${dog.chipped}")
        } else {
            throw IllegalArgumentException("Given animal is not a dog!")
        }
    }

    // Wir können hier also zusammenfassen:
    // Immer die Typen so konkret wie möglich benutzen, um fachlich möglichst korrekt zu sein.
    // Auch bei abstrakten Zusammenfassungen nimmer man immer den konkretesten, gemeinsamen Typ.
}