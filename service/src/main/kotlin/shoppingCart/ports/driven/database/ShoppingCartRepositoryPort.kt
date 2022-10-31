package shoppingCart.ports.driven.database

import shoppingCart.domain.ShoppingCart
import shoppingCart.domain.ShoppingCartUuid
import java.util.Optional

interface ShoppingCartRepositoryPort {
    fun save(shoppingCart: ShoppingCart)

    fun load(shoppingCartUuid: ShoppingCartUuid): ShoppingCart?
}