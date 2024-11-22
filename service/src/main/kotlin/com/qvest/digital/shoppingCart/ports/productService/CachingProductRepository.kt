package com.qvest.digital.shoppingCart.ports.productService

import org.springframework.cache.annotation.Cacheable

open class CachingProductRepository(private val delegate: ProductRepositoryPort) :
    ProductRepositoryPort {

    @Cacheable("products")
    override fun findProductBySku(sku: com.qvest.digital.shoppingCart.domain.SKU): com.qvest.digital.shoppingCart.domain.Product? {
        return delegate.findProductBySku(sku)
    }

}