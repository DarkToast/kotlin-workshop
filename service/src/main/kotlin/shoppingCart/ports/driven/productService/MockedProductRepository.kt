package shoppingCart.ports.driven.productService

import mu.KotlinLogging
import shoppingCart.domain.Name
import shoppingCart.domain.Price
import shoppingCart.domain.Product
import shoppingCart.domain.SKU
import java.util.Optional

class MockedProductRepository : ProductRepositoryPort {
    private val logger = KotlinLogging.logger {}

    override fun findProductBySku(sku: SKU): Product? {
        logger.info { "Receiving product '$sku' from mock" }

        return when (sku) {
            SKU("123456") -> Product(SKU("123456"), Price(4, 99), Name("Brot"))
            SKU("654321") -> Product(SKU("654321"), Price(2, 99), Name("Milch"))
            SKU("000000") -> Product(SKU("000000"), Price(29, 99), Name("Champagner"))
            else -> null
        }
    }
}