package de.tarent.ciwanzik.shoppingCart.ports.driven.productService

import de.tarent.ciwanzik.shoppingCart.domain.Product
import de.tarent.ciwanzik.shoppingCart.domain.SKU
import java.util.*

interface ProductRepositoryPort {
    fun findProductBySku(sku: SKU): Optional<Product>
}