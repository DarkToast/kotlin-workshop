package de.tarent.ciwanzik.shoppingCart.ports.driven.productService

import de.tarent.ciwanzik.shoppingCart.domain.Name
import de.tarent.ciwanzik.shoppingCart.domain.Price
import de.tarent.ciwanzik.shoppingCart.domain.Product
import de.tarent.ciwanzik.shoppingCart.domain.SKU
import java.util.*

class MockedProductRepository: ProductRepositoryPort {
    override fun findProductBySku(sku: SKU): Optional<Product> {
        return when(sku) {
            SKU("123456") -> Optional.of(Product(SKU("123456"), Price(4, 99), Name("Brot")))
            SKU("654321") -> Optional.of(Product(SKU("654321"), Price(2, 99), Name("Milch")))
            SKU("000000") -> Optional.of(Product(SKU("000000"), Price(29, 99), Name("Champagner")))
            else -> Optional.empty()
        }
    }
}