package de.tarent.ciwanzik.shoppingCart.domain

import java.lang.RuntimeException
import java.util.*

class MaximumProductCountExceededException(productCount: Int):
        RuntimeException("The maximum product count of 50 was exceeded. Actual: '$productCount'")

class ShoppingCart(
    val shoppingCartUuid: ShoppingCartUuid = ShoppingCartUuid(),
    private val cartItems: MutableMap<Product, Quantity> = mutableMapOf()
) {
    fun amount(): ShoppingCartAmount = TODO()

    fun isEmpty(): Boolean = TODO()

    fun putProductInto(product: Product, quantity: Quantity): ShoppingCart = TODO()

    fun quantityOfProduct(sku: SKU): Optional<Quantity> = TODO()

    fun content(): List<Pair<Product, Quantity>> = TODO()
}

data class ShoppingCartUuid(val uuid: UUID = UUID.randomUUID()) {

    constructor(uuid: String): this(UUID.fromString(uuid))

    override fun toString(): String = uuid.toString()
}