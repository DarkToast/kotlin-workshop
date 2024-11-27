package com.qvest.digital.shoppingCart.ports.product

import com.qvest.digital.shoppingCart.domain.Product
import com.qvest.digital.shoppingCart.domain.SKU

interface ProductPort {
    fun findProductBySku(sku: SKU): Product?
}