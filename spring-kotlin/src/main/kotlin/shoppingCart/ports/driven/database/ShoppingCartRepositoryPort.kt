package de.tarent.ciwanzik.shoppingCart.ports.driven.database

import de.tarent.ciwanzik.shoppingCart.domain.ShoppingCart
import de.tarent.ciwanzik.shoppingCart.domain.ShoppingCartUuid
import java.util.*

interface ShoppingCartRepositoryPort {
    fun save(shoppingCart: ShoppingCart)

    fun load(shoppingCartUuid: ShoppingCartUuid): Optional<ShoppingCart>
}