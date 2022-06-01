package shoppingCart.application

import shoppingCart.domain.ShoppingCart
import shoppingCart.domain.ShoppingCartUuid
import java.util.Optional

interface ShoppingCartService {
    fun takeNewShoppingCart(): ShoppingCart

    fun showShoppingCart(shoppingCartUuid: ShoppingCartUuid): ShoppingCart?
}