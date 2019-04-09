package de.tarent.ciwanzik.shoppingCart.domain

import java.lang.RuntimeException

class TooHighPriceException(value: Int): RuntimeException("Price must not exceed 120,00€. Actual: '$value'")

class MaximumShoppingCardAmountExceededException(value: Int):
        RuntimeException("The maximum shopping card amount of 300,00€ exceeded. Actual: '$value'")

sealed class Money<T: Money<T>> (euro: Int, cent: Int) {
    val valueInCent = euro * 100 + cent

    abstract operator fun plus(money: Money<*>): T
}

data class Price(val euro: Int, val cent: Int) {
    val valueInCent = 0
    fun plus(price: Price): Price = this

    fun times(quantity: Quantity): ShoppingCartAmount = ShoppingCartAmount(0, 0)
}

data class ShoppingCartAmount(val euro: Int, val cent: Int) {
    val valueInCent = 0
    fun plus(amount: ShoppingCartAmount): ShoppingCartAmount = this
}