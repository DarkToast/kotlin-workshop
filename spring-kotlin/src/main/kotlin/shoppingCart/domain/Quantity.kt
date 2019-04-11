package de.tarent.ciwanzik.shoppingCart.domain

import java.lang.IllegalArgumentException

data class Quantity(val value: Int) {
    init {
        if(value < 0) throw IllegalArgumentException("Quantity must not be zero")
        if(value > 10) throw TooMuchItemsOfAProduct(value)
    }
}

class TooMuchItemsOfAProduct(quantity: Int): DomainException("Quantity must not exceed 10. Is '$quantity'")