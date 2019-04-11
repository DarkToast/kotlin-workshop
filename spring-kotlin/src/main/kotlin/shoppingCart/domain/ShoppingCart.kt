package de.tarent.ciwanzik.shoppingCart.domain

import java.lang.RuntimeException
import java.util.*

class MaximumProductCountExceededException(productCount: Int):
        DomainException("The maximum product count of 50 was exceeded. Actual: '$productCount'")

class ShoppingCart(
    val shoppingCartUuid: ShoppingCartUuid = ShoppingCartUuid(),
    private val cartItems: MutableMap<Product, Quantity> = mutableMapOf()
) {
    fun amount(): ShoppingCartAmount = ShoppingCartAmount(0, 0)

    fun isEmpty(): Boolean = false

    fun putProductInto(product: Product, quantity: Quantity): ShoppingCart = this

    fun quantityOfProduct(sku: SKU): Optional<Quantity> = Optional.empty()

    fun content(): List<Pair<Product, Quantity>> = listOf()
}

data class ShoppingCartUuid(val uuid: UUID = UUID.randomUUID()) {

    constructor(uuid: String): this(UUID.fromString(uuid))

    override fun toString(): String = uuid.toString()
}