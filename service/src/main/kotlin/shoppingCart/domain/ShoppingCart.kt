package shoppingCart.domain

import java.util.Optional
import java.util.UUID

class MaximumProductCountExceededException(productCount: Int):
        DomainException("The maximum product count of 50 was exceeded. Actual: '$productCount'")

class ShoppingCart(
        val shoppingCartUuid: ShoppingCartUuid = ShoppingCartUuid(),
        private val cartItems: MutableMap<Product, Quantity> = mutableMapOf()
) {

    private var overallAmount: ShoppingCartAmount = cartItems
            .map { item -> item.key.price * item.value }
            .fold(ShoppingCartAmount(0, 0)) { overall, shoppingCartAmount -> overall + shoppingCartAmount }

    fun amount(): ShoppingCartAmount = overallAmount.copy()

    fun isEmpty(): Boolean = cartItems.isEmpty()

    fun putProductInto(product: Product, quantity: Quantity): ShoppingCart {
        checkMaximumProductCount()

        val newAmount: ShoppingCartAmount = overallAmount + (product.price * quantity)
        val existingQuantity: Quantity? = cartItems[product]

        if(existingQuantity == null) {
            cartItems[product] = quantity
        } else {
            cartItems[product] = existingQuantity.copy(value = existingQuantity.value + quantity.value)
        }

        overallAmount = newAmount

        return this
    }

    fun quantityOfProduct(sku: SKU): Optional<Quantity> {
        return Optional.ofNullable(cartItems.mapKeys { item -> item.key.sku }[sku])
    }

    fun content(): List<Pair<Product, Quantity>> {
        return cartItems.map { entry -> Pair(entry.key, entry.value) }
    }


    private fun checkMaximumProductCount() {
        if(cartItems.count() >= 50) {
            throw MaximumProductCountExceededException(cartItems.count())
        }
    }


    // -- Equals - HashCode --

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShoppingCart

        if (shoppingCartUuid != other.shoppingCartUuid) return false
        if (cartItems != other.cartItems) return false
        if (overallAmount != other.overallAmount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = shoppingCartUuid.hashCode()
        result = 31 * result + cartItems.hashCode()
        result = 31 * result + overallAmount.hashCode()
        return result
    }
}

data class ShoppingCartUuid(val uuid: UUID = UUID.randomUUID()) {

    constructor(uuid: String): this(UUID.fromString(uuid))

    override fun toString(): String = uuid.toString()
}