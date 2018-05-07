package session1


/*
Jedwede Parameterlisten können in Kotlin mit `default parameters` angereichtert werden.
Im folgenden Beispiel setzen für den Value `b` den Defaultwert 5.
 */
fun area(a: Int, b: Int = 5): Int {
    return a * b
}

/*
Beim Aufruf können wir nun die Funktion auf drei Arten aufrufen:
- In der "klassischen" Varianten werden einfach beide Werte übergeben.
- In der zweiten Variante benutzen wir den Defaultwert für `b` und übergeben lediglich den Wert `a`.
- In der dritten Variante benutzen wir das Feature `named arguments`. Somit können wir direkt steuern in
  welcher Reihenfolge wir die Parameter übergeben können.
  --> Der Code "kann" dadurch lesbarer werden, z.B. wenn man einige Booleanwerte übergibt.
        `getCustomer(true, false, true)` vs. `getCustomer(hasContract = true, superUser = false, enabled = true)`
      Der Code "kann" dadurch aber auch unlesbarer werden, wenn man z.B. in jedem Aufruf die Reihenfolge ändert.
 */
fun main(args: Array<String>) {
    val field1 = area(10, 20)
    val field2 = area(5)
    val field3 = area(b = 2, a = 3)
}


/*
Kotlin erlaubt das verschachteln von Funktionen.
In diesem Beispiel soll eine Int-Liste rekursiv summiert werden. Da wir den Machnismus der Tailrecurstion benutzen
wollen, definieren wir uns eine lokale Hilfsfunktion, die für uns die rekursive Summierung übernimmt.
 */
fun recursiveSum(elements: List<Int>): Int {

    fun recursiveStep(accumulator: Int, remainingList: List<Int>): Int {

        // In Kotlin sind IFs expressions und besitzen einen "Rückgabewert", der immer dem letzten Statement
        // eines Blocks entspricht.
        return if(remainingList.isEmpty()) {
            accumulator
        } else {
            recursiveStep(accumulator + remainingList.first(), remainingList.drop(1))
        }
    }

    return recursiveStep(0, elements)
}