package shoppingCart.domain

class TooHighPriceException(value: Int) : DomainException("Price must not exceed 120,00€. Actual: '$value'")

class MaximumShoppingCardAmountExceededException(value: Int) :
    DomainException("The maximum shopping card amount of 300,00€ exceeded. Actual: '$value'")

/**
 * TODO() ist eine Funktion, welche immer einen NotImplementedError wirft.
 * Damit kann man schon einmal Stubs für Tests etc. schreiben, ohne sich Gedanken um die Implementierung zu machen.
 */
class Price(euro: Int, cent: Int) {
    val valueInCent = 0
    fun plus(price: Price): Price = TODO()
    fun times(quantity: Quantity): Amount = TODO()
}

/**
 * Zusatz:
 *
 * Kotlin kennt Operatorüberladung. Allerdings können nur ein paar spezielle Methoden
 * angewendet werden. `plus` und `times` sind zwei davon. Versuchen sie mittels des `operator` Modifiers vor dem `fun`
 * Statement die Methoden in Operatoren zu verwandeln und schreiben Sie einen Test der zwei ShoppingCartAmounts
 * lediglich über den `+` Operator addiert.
 *
 * Eine Liste der Methoden:
 * https://kotlinlang.org/docs/reference/operator-overloading.html
 */
data class Amount(val euro: Int, val cent: Int) {
    val valueInCent = 0
    fun plus(money: Amount): Amount = TODO()
}