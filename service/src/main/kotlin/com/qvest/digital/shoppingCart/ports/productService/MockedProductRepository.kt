package com.qvest.digital.shoppingCart.ports.productService

import com.qvest.digital.shoppingCart.domain.money.Price
import io.github.oshai.kotlinlogging.KotlinLogging

class MockedProductRepository : ProductRepositoryPort {
    private val logger = KotlinLogging.logger {}

    override fun findProductBySku(sku: com.qvest.digital.shoppingCart.domain.SKU): com.qvest.digital.shoppingCart.domain.Product? {
        logger.info { "Receiving product '$sku' from mock" }

        return when (sku) {
            com.qvest.digital.shoppingCart.domain.SKU("123456") -> com.qvest.digital.shoppingCart.domain.Product(
                com.qvest.digital.shoppingCart.domain.SKU(
                    "123456"
                ), Price(4, 99), com.qvest.digital.shoppingCart.domain.Name("Brot")
            )

            com.qvest.digital.shoppingCart.domain.SKU("654321") -> com.qvest.digital.shoppingCart.domain.Product(
                com.qvest.digital.shoppingCart.domain.SKU(
                    "654321"
                ), Price(2, 99), com.qvest.digital.shoppingCart.domain.Name("Milch")
            )

            com.qvest.digital.shoppingCart.domain.SKU("000000") -> com.qvest.digital.shoppingCart.domain.Product(
                com.qvest.digital.shoppingCart.domain.SKU(
                    "000000"
                ), Price(29, 99), com.qvest.digital.shoppingCart.domain.Name("Champagner")
            )

            else -> null
        }
    }
}