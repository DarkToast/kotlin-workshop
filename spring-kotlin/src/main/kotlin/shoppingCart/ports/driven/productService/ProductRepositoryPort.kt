package shoppingCart.ports.driven.productService

import shoppingCart.domain.Product
import shoppingCart.domain.SKU
import java.util.Optional

interface ProductRepositoryPort {
    fun findProductBySku(sku: SKU): Optional<Product>
}