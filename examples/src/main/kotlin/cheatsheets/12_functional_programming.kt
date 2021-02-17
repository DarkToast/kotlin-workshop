package cheatsheets

/**
 * Themen:
 *  - Funktionstypen
 *  - Funktionen als first class citizens
 *  - Higher order functions
 *  - Typ aliases
 */

// Typealiases ermöglich eine sytaktische Abkürzung. Sie bilden aber keinen neuen Typ!
typealias EmployeeMapper = (Employee) -> Employee

fun main(args: Array<String>) {
    functions()
    higherOrderFunctions()
}

fun functions() {
    val list: List<String> = listOf("blue", "green", "red")

    // Funktionen, welche wir an andere Funktionen übergeben, haben für sich genommen ebenfalls einen Typ.
    // Im Fall der `filter`-Methode von `List` ist der Typ des Lambdaparameters `(T) -> kotlin.Boolean`.
    // `T` definiert dabei den Generic-Typ der Liste. In unserem Fall `String`
    list.filter({ value -> value == "blue" })

    // Da Funktionen einen Typ haben, können wir sie auch bennen und wie jeden anderen Wert benutzen. Also wie ein
    // `String`, `Boolean` oder Ähnliches. Funktionen sind in Kotlin daher "first class citizens".
    // Als Beispiel extrahieren wir unseren Lambda von weiter oben in einen eigenen Wert mit zugehörigem Typ:

    //   + Name der Funktion
    //   |
    //   |         + Typ der Funktion. Gesprochen "String auf Boolean"
    //   |         | Dieser Typ definiert alle Funktionen, welche einen `String` in einen `Boolean` umwandeln können.
    //   |         |
    //   |         |                     + Die eigentliche Funktion. `value: String` ist dabei der erste Eingabeparameter.
    //   |         |                     | `value == "blue"` definiert den Funktionskörper.
    //   |         |                     |
    val filterFun: (String) -> Boolean = { value: String -> value == "blue" }

    // `filterFun` können wir nun direkt auf `Liste::filter` anstatt des Lambdas anwenden. Und da wir sie in einem `val`
    // gespeichert haben auch an jeder anderen Stelle wiederverwenden.
    list.filter(filterFun)
}

/**
 * Da wir Funktionen in Kotlin als normale Werte betrachten, können wir sie auch als Parameter und Rückgabewerte
 * von anderen Funktionen benutzen. Diese "anderen" Funktionen nennt man "higher order functions". Mit diesen Funktionskonzepten
 * erfüllen wir nebenbei bereits die zentralen Eigenschaften von funktionalen Programmiersprachen.
 */
fun higherOrderFunctions() {

    /**
     * Übernimmt als ersten Parameter eine Funktion, die einen `Employee` auf ein `Boolean` matched. z.B. eine
     * Wertüberprüfung oder eine Whitelist von bestimmten Personen.
     *
     * Der zweite Parameter ist ein `vararg`. Mit diesem Modifier definieren wir employees als repeated parameter.
     * Wie können hier also beliebig viele `Employees` übergeben. `employees` ist dann vom Typ Array<Employee>
     *
     * Innerhalb des Bodys wird matcher auf alle übergebenden `Employee`s angewandt. Wenn er für alle `true` zurück gibt,
     * so gibt auch `matchEmployees` true zurück. Sonst false.
     */
    fun matchEmployees(matcher: (Employee) -> Boolean, vararg employees: Employee): Boolean {
        for (employee in employees) {
            if (!matcher(employee)) {
                return false
            }
        }

        return true
    }

    /**
     * `createSalaryIncrease` erzeugt und gibt eine neue Funktion zurück, welche einen `Employee` entgegen nimmt und ihm
     * die `Salary` um den Faktor `factor` erhöht.
     *
     * `factor` ist nicht direkt in der neu erzeugten Funktion definiert, sondern wird aus dem Scope von
     * `createSalaryIncrease` übernommen. So einen Wert nennt man "Closure".
     * Egal ob `createSalaryIncrease` beendet ist der die erzeugte Funktion durch alle Schichten der Applikation
     * geschleust wird: Der Wert für `factor` bleibt immer im Scope der neuen Funktion erhalten.
     */
    fun createSalaryIncrease(factor: Float): EmployeeMapper {

        return { employee: Employee ->
            val newSalary: Int = (employee.salary.value * factor).toInt()
            employee.copy(salary = Salary(newSalary))
        }
    }

    // Nun können wir mit diesen Hilfsmitteln auf funktionale Weise und Programm konfigurieren:

    // Zunächst erzeugen wir uns mit `createSalaryIncrease` eine Funktion für das Jahr 2018. In diesem Jahr soll
    // jeder Mitarbeiter eine 10%ige Lohnerhöhung bekommen. Da werden sich sicher alle freuen. :-)
    val salaryIncrease2018: (Employee) -> Employee = createSalaryIncrease(1.1F)

    // Damit wir aber nicht zu viel erhöhen, definieren wir uns noch eine Überprüfungsfunktion, welche ein Limit
    // auf 100.000€ setzt:
    val salaryBelow100k: (Employee) -> Boolean = { employee -> employee.salary.value <= 100000 }

    // Nun erhöhen wir auch direkt mal den Lohn von Max und Yvonne aus unserem data class Beispiel.
    val increasedMax = salaryIncrease2018(max)
    val increasedYvonne = salaryIncrease2018(yvonne)

    // Danach geben wir noch unsere Überprüfungsfunktion an `matchEmployees` und überprüfen Max und Yvonne.
    // Oh je. Einer von beiden verdient zu viel... --> false
    val allInSalaryRange = matchEmployees(salaryBelow100k, increasedMax, increasedYvonne)
}
