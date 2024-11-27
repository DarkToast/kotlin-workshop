package com.qvest.digital.shoppingCart.ports.product

import com.qvest.digital.shoppingCart.domain.Product
import org.springframework.cache.annotation.Cacheable

open class CachingAdapter(private val delegate: ProductPort) : ProductPort {

    @Cacheable("products")
    override fun findProductBySku(sku: com.qvest.digital.shoppingCart.domain.SKU): Product? {
        return delegate.findProductBySku(sku)
    }
}