package shoppingCart.ports.driven.productService

import shoppingCart.domain.Name
import shoppingCart.domain.Price
import shoppingCart.domain.Product
import shoppingCart.domain.SKU
import java.util.Optional

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