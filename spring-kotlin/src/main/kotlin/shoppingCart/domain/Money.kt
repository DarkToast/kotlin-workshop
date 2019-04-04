package de.tarent.ciwanzik.shoppingCart.domain

import java.lang.RuntimeException

class TooHighPriceException(value: Int): RuntimeException("Price must not exceed 120,00€. Actual: '$value'")

class MaximumShoppingCardAmountExceededException(value: Int):
        RuntimeException("The maximum shopping card amount of 300,00€ exceeded. Actual: '$value'")

data class Price(val euro: Int, val cent: Int)

data class ShoppingCartAmount(val euro: Int, val cent: Int)