package shoppingCart.ports.driven.productService

import shoppingCart.domain.Product
import shoppingCart.domain.SKU
import org.springframework.cache.annotation.Cacheable
import java.util.*

open class CachingProductRepository(private val delegate: ProductRepositoryPort): ProductRepositoryPort {

    @Cacheable("products")
    override fun findProductBySku(sku: SKU): Optional<Product> {
        return delegate.findProductBySku(sku)
    }

}