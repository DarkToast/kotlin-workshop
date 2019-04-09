package de.tarent.ciwanzik.shoppingCart.ports.driver.rest.dto

import de.tarent.ciwanzik.shoppingCart.domain.Product
import de.tarent.ciwanzik.shoppingCart.domain.ShoppingCart
import de.tarent.ciwanzik.shoppingCart.domain.ShoppingCartAmount
import de.tarent.ciwanzik.shoppingCart.ports.driver.rest.dto.Method.GET
import java.net.URI
import java.util.*

data class Item(val quantity: Int, val product: Product)

data class ShoppingCartDto(
    val uuid: UUID,
    val amount: ShoppingCartAmount,
    val items: List<Item>
): Linked<ShoppingCartDto>() {

    private fun withLinks(): ShoppingCartDto {
        return addLink("self", GET, URI("/shoppingcart/${uuid}"))
            .addLink("addProduct", Method.PUT, URI("/shoppingcart/${uuid}"))
    }

    companion object {
        fun fromDomain(shoppingCart: ShoppingCart): ShoppingCartDto {
            val amount = shoppingCart.amount()
            val items = shoppingCart.content().map { pair ->
                Item(pair.second.value, pair.first)
            }

            val uuid = shoppingCart.shoppingCartUuid.uuid
            return ShoppingCartDto(uuid, amount, items).withLinks()
        }
    }
}
