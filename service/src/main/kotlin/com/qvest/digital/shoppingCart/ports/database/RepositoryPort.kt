package com.qvest.digital.shoppingCart.ports.database

import com.qvest.digital.shoppingCart.domain.ShoppingCart
import com.qvest.digital.shoppingCart.domain.ShoppingCartUuid

interface RepositoryPort {
    fun save(shoppingCart: ShoppingCart)

    fun load(shoppingCartUuid: ShoppingCartUuid): ShoppingCart?
}