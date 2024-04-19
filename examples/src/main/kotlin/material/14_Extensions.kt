package material

/**
 * Kotlin erlaubt es, bestehende Klassen um neue Methoden zu erweitern. Diese "Extensions" sind dabei nur dort
 * gültig, in dessen Scope sie auch definiert wurden. Die Beispiele hier, sind im globalen Scope definiert und daher
 * auch von überall aus erreichbar.
 *
 * Als Beispiel ziehen wir unsere Java Klasse aus section1 zur Rate. Diese Klasse ist final und kann nicht abgeleitet werden.
 * Mit Kotlin "Extensions" können wir aber trotzdem eine neue Methode hinzufügen.
 * Dafür definieren wir folgende Methode:
 *  - Als Name geben wir die Zielklasse und die gewünschte neue Methode an.
 *  - Der Rest, also Rückgabetyp und Parametertypen bleibt gleich
 *  - Innerhalb der Methode existiert eine `this`-Referenz, die auf das entsprechende Objekt des erweiterten Typs
 *    zeigt.
 *
 * "Extensions" können nur auf die `public`-Elemente des Zieltyps zugreifen. Intern handelt es sich dabei lediglich
 * um einen Wrappertyp, der die neuen Methoden beinhaltet. Der Compiler bietet es uns lediglich als Teil der Zielklasse an.
 */
fun Contract.getBaseFeePerYear(): Double {
    val baseFee: Double = this.getBaseFee() ?: 0.0
    return baseFee * 12
}

fun Contract.getReducedYearBaseFee(): Double {
    return this.getBaseFeePerYear() * 0.95
}

fun main() {
    // Wenn wir nun ein konkretes Contract Objekt besitzen...
    val contract = Contract()

    // ... so können wir neben den eigentlichen Methoden auch die erweiteren Methoden benutzen.
    println(contract.getBaseFee())
    println(contract.getBaseFeePerYear())
    println(contract.getReducedYearBaseFee())
}

