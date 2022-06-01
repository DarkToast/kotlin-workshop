package shoppingCart.domain

import java.util.Optional
import java.util.UUID

class MaximumProductCountExceededException(productCount: Int) :
    DomainException("The maximum product count of 50 was exceeded. Actual: '$productCount'")

data class Item(val product: Product, val quantity: Quantity) {
    val amount: Amount = product.price.times(quantity)

    fun addQuantity(q: Quantity) = Item(
        product = product,
        quantity = this.quantity + q
    )
}

class ShoppingCart(
    val shoppingCartUuid: ShoppingCartUuid = ShoppingCartUuid(),
    private val items: List<Item> = emptyList()
) {

    fun items() = items

    fun amount(): Amount = Amount(0, 0)

    fun isEmpty(): Boolean = false

    fun addProduct(product: Product, quantity: Quantity): ShoppingCart = this

    fun quantityOfProduct(sku: SKU): Optional<Quantity> = Optional.empty()

    fun content(): List<Pair<Product, Quantity>> = listOf()
}

data class ShoppingCartUuid(val uuid: UUID = UUID.randomUUID()) {
    constructor(uuid: String) : this(UUID.fromString(uuid))

    override fun toString(): String = uuid.toString()
}