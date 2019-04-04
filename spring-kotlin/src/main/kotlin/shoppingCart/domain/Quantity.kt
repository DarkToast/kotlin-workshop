package de.tarent.ciwanzik.shoppingCart.domain

import java.lang.IllegalArgumentException

data class Quantity(val value: Int)

class TooMuchItemsOfAProduct(quantity: Int): RuntimeException("Quantity must not exceed 10. Is '$quantity'")