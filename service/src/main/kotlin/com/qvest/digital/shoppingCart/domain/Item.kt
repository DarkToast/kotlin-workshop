package com.qvest.digital.shoppingCart.domain

import com.qvest.digital.shoppingCart.domain.money.Amount

data class Item(val product: Product, val quantity: Quantity) {
    val amount: Amount = product.price * quantity

    fun addQuantity(q: Quantity) = this.copy(
        quantity = this.quantity + q
    )
}