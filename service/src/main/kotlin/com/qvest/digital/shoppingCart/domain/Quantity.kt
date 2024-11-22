package com.qvest.digital.shoppingCart.domain

data class Quantity(val value: Int) {
    init {
        if (value < 0) throw IllegalArgumentException("Quantity must not be zero")
        if (value > 10) throw TooMuchItemsOfAProduct(value)
    }

    operator fun plus(other: Quantity) = Quantity(value + other.value)
}

class TooMuchItemsOfAProduct(quantity: Int) : DomainException("Quantity must not exceed 10. Is '$quantity'")