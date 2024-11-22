package com.qvest.digital.shoppingCart.ports.productService

import com.qvest.digital.shoppingCart.domain.Product
import com.qvest.digital.shoppingCart.domain.SKU

interface ProductRepositoryPort {
    fun findProductBySku(sku: SKU): Product?
}