package cheatsheets

/**
 * Delegations bieten in Kotlin syntactic sugar für delegate objects, sodass man ein direktes Durchschleifen
 * elegant abkürzen kann und einem den Grundsatz "Composition over Inheritance" wesentlich komfortabler zu gestalten.
 *
 * Ein Interface, welches die Methoden `printMessage` und `printMessageLine` definiert.
 */
interface Printer {
    fun printMessage(message: String)
    fun printMessageLine(message: String)
}

/**
 * Diese Implementierung schreibt alle Eingaben einfach auf Stdout. Mal mit einem Newline, mal ohne.
 */
class StdOutPrinter : Printer {
    override fun printMessage(value: String) = print(value)
    override fun printMessageLine(value: String) = println(value)
}

/**
 * Nun definieren wir uns eine Delegierungsfassade.
 * Anstatt von StdOutPrinter abzuleiten, verwenden wir hier die Delegierung.
 * - Per Konstruktorargument erhalten wir zunächst das Zielobjekt der Delegierung. Einen `Printer`.
 * - Wir "implementieren" daraufhin das Interface, geben aber per `by` unser Delegierungsobjekt aus dem Konstruktor an.
 *   Kotlin wird nun `PrinterFacade` zum `Printer` machen und alle nicht selbst implementierten Methoden des Interfaces
 *   automatisch an unser Zielobjekt delegieren.
 * - Syntaktisch sieht es nun fast wie eine Ableitung aus. Wie sind nun aber wesentlich flexibler da wir selbst
 *   entscheiden können, zu welchem Zielobjekt wir delegieren können.
 */
class PrinterFacade(printer: Printer) : Printer by printer {
    override fun printMessageLine(message: String) = println("Hello $message")
}

/**
 * Wir komponieren uns nun einen Printer, bestehend aus einer `PrinterFacade` und einem `StdOutPrinter` als `Delegierungsobjekt.
 * - Der Aufruf von `printMessage` wird nun die Implementierung von `StdOutPrinter` nutzen.
 * - Der Aufruf von `printMessageLine` wird die Implementierung von `PrinterFacade` nutzen.
 */
fun main() {
    val printer: Printer = PrinterFacade(StdOutPrinter())
    printer.printMessage("Hallo Welt")
    printer.printMessageLine("Hallo Welt")
}
