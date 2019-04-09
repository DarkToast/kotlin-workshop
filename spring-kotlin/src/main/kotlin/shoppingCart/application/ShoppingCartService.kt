package shoppingCart.application

import shoppingCart.domain.Quantity
import shoppingCart.domain.SKU
import shoppingCart.domain.ShoppingCart
import shoppingCart.domain.ShoppingCartUuid
import java.util.*

interface ShoppingCartService {
    fun putProductIntoShoppingCart(shoppingCartUuid: ShoppingCartUuid, productSku: SKU, quantity: Quantity): Optional<ShoppingCart>

    fun takeNewShoppingCart(): ShoppingCart

    fun showShoppingCart(shoppingCartUuid: ShoppingCartUuid): Optional<ShoppingCart>
}