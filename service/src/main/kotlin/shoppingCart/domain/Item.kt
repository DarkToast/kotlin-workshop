package shoppingCart.domain

import shoppingCart.domain.money.Amount

data class Item(val product: Product, val quantity: Quantity) {
    val amount: Amount = product.price * quantity

    fun addQuantity(q: Quantity) = this.copy(
        quantity = this.quantity + q
    )
}