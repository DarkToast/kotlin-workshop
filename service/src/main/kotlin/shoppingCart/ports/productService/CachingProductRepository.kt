package shoppingCart.ports.productService

import org.springframework.cache.annotation.Cacheable
import shoppingCart.domain.Product
import shoppingCart.domain.SKU
import java.util.Optional

open class CachingProductRepository(private val delegate: ProductRepositoryPort) : ProductRepositoryPort {

    @Cacheable("products")
    override fun findProductBySku(sku: SKU): Product? {
        return delegate.findProductBySku(sku)
    }

}