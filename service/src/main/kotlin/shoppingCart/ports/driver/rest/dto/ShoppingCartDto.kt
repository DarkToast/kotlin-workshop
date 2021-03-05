package shoppingCart.ports.driver.rest.dto

import shoppingCart.domain.ShoppingCart
import shoppingCart.domain.ShoppingCartAmount
import shoppingCart.ports.driver.rest.dto.Method.GET
import java.net.URI
import java.util.UUID

data class Item(val quantity: Int, val product: GetProduct)

data class ShoppingCartDto(
    val uuid: UUID,
    val amount: ShoppingCartAmount,
    val items: List<Item>
): Linked<ShoppingCartDto>() {

    private fun withLinks(): ShoppingCartDto {
        return addLink("self", GET, URI("/shoppingcart/${uuid}"))
            .addLink("addProduct", Method.PUT, URI("/shoppingcart/${uuid}/items"))
    }

    companion object {
        fun fromDomain(shoppingCart: ShoppingCart): ShoppingCartDto {
            val amount = shoppingCart.amount()
            val items = shoppingCart.content().map { pair ->
                Item(pair.second.value, GetProduct(pair.first.sku.value, pair.first.name.value))
            }

            val uuid = shoppingCart.shoppingCartUuid.uuid
            return ShoppingCartDto(uuid, amount, items).withLinks()
        }
    }
}

