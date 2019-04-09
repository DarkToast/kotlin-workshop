package de.tarent.ciwanzik.shoppingCart.domain

import java.lang.RuntimeException
import java.util.function.ToDoubleBiFunction

class TooHighPriceException(value: Int): RuntimeException("Price must not exceed 120,00€. Actual: '$value'")

class MaximumShoppingCardAmountExceededException(value: Int):
        RuntimeException("The maximum shopping card amount of 300,00€ exceeded. Actual: '$value'")

class Price(euro: Int, cent: Int) {
    val valueInCent = 0
    fun plus(price: Price): Price = TODO()
    fun times(quantity: Quantity): ShoppingCartAmount = TODO()
}

class ShoppingCartAmount(euro: Int, cent: Int) {
    val valueInCent = 0
    fun plus(amount: ShoppingCartAmount): ShoppingCartAmount = TODO()
}