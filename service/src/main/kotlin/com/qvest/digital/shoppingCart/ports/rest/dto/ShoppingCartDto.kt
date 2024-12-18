package com.qvest.digital.shoppingCart.ports.rest.dto

import com.qvest.digital.shoppingCart.domain.ShoppingCart
import com.qvest.digital.shoppingCart.domain.money.Amount
import com.qvest.digital.shoppingCart.ports.rest.dto.Method.GET
import java.net.URI
import java.util.UUID

data class ShoppingCartDto(
    val uuid: UUID,
    val amount: Amount,
    val items: List<GetItem>
) : Linked<ShoppingCartDto>() {

    private fun withLinks(): ShoppingCartDto =
        addLink("self", GET, URI("/shoppingcart/${uuid}"))
            .addLink("addProduct", Method.PUT, URI("/shoppingcart/${uuid}/items"))

    companion object {
        fun fromDomain(shoppingCart: ShoppingCart): ShoppingCartDto {
            val amount = shoppingCart.amount()
            val items = shoppingCart.items().map { dItem ->
                GetItem(dItem.quantity.value, GetProduct(dItem.product.sku.value, dItem.product.name.value))
            }

            val uuid = shoppingCart.shoppingCartUuid.uuid
            return ShoppingCartDto(uuid, amount, items).withLinks()
        }
    }
}

