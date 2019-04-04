package de.tarent.ciwanzik.shoppingCart.application

import de.tarent.ciwanzik.shoppingCart.domain.Quantity
import de.tarent.ciwanzik.shoppingCart.domain.SKU
import de.tarent.ciwanzik.shoppingCart.domain.ShoppingCart
import de.tarent.ciwanzik.shoppingCart.domain.ShoppingCartUuid
import java.util.*

interface ShoppingCartService {
    fun putProductIntoShoppingCart(shoppingCartUuid: ShoppingCartUuid, productSku: SKU, quantity: Quantity): Optional<ShoppingCart>

    fun takeNewShoppingCart(): ShoppingCart

    fun showShoppingCart(shoppingCartUuid: ShoppingCartUuid): Optional<ShoppingCart>
}