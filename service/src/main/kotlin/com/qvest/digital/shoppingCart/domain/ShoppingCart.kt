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
    items: List<Item> = emptyList()
) {
    private var cartItems: Map<SKU, Item> = items.associateBy { it.product.sku }
        set(value) {
            amount = value.amount()
            field = value
        }

    private var amount: Amount = cartItems.amount()

    private fun Map<SKU, Item>.amount() =
        this.values.fold(Amount(0, 0)) { amount, item ->
            amount + item.amount
        }

    fun amount(): Amount = amount

    fun isEmpty(): Boolean = cartItems.isEmpty()

    fun addProduct(product: Product, quantity: Quantity): ShoppingCart {
        checkMaximumProductCount()

        val mutatedItems = cartItems.toMutableMap()

        val item = mutatedItems[product.sku]
            ?.addQuantity(quantity)
            ?: Item(product, quantity)

        mutatedItems[product.sku] = item

        cartItems = mutatedItems
        return this
    }

    fun quantityOfProduct(sku: SKU): Quantity? {
        return cartItems[sku].let { it?.quantity }
    }

    fun items(): List<Item> {
        return cartItems.values.toList()
    }

    private fun checkMaximumProductCount() {
        if (cartItems.count() >= 50) {
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

        return true
    }

    override fun hashCode(): Int {
        var result = shoppingCartUuid.hashCode()
        result = 31 * result + cartItems.hashCode()
        return result
    }

    override fun toString(): String {
        return "ShoppingCart(shoppingCartUuid=$shoppingCartUuid)"
    }
}

