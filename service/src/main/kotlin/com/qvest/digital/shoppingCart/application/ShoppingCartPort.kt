package com.qvest.digital.shoppingCart.application

import com.qvest.digital.shoppingCart.domain.Quantity
import com.qvest.digital.shoppingCart.domain.SKU
import com.qvest.digital.shoppingCart.domain.ShoppingCart
import com.qvest.digital.shoppingCart.domain.ShoppingCartUuid

interface ShoppingCartPort {
    fun takeNewShoppingCart(): ShoppingCart

    fun showShoppingCart(shoppingCartUuid: ShoppingCartUuid): ShoppingCart?

    fun putProductIntoShoppingCart(shoppingCartUuid: ShoppingCartUuid, productSku: SKU, quantity: Quantity): ShoppingCart?
}