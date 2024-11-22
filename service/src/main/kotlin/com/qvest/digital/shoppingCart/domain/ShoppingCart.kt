package com.qvest.digital.shoppingCart.domain

import com.qvest.digital.shoppingCart.DomainException
import com.qvest.digital.shoppingCart.domain.money.Amount
import java.util.UUID

class MaximumProductCountExceededException(productCount: Int) :
    DomainException("The maximum product count of 50 was exceeded. Actual: '$productCount'")

data class ShoppingCartUuid(val uuid: UUID = UUID.randomUUID()) {
    override fun toString(): String = uuid.toString()
}

class ShoppingCart(
    val shoppingCartUuid: ShoppingCartUuid = ShoppingCartUuid(),
    private val items: List<Item> = emptyList()
) {

    fun items() = items

    fun amount(): Amount = Amount(0, 0)

    fun isEmpty(): Boolean = false

    fun addProduct(product: Product, quantity: Quantity): ShoppingCart = this

    fun quantityOfProduct(sku: SKU): Quantity? = null

    fun content(): List<Pair<Product, Quantity>> = listOf()
}

