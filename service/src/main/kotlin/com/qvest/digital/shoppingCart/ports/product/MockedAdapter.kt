package com.qvest.digital.shoppingCart.ports.product

import com.qvest.digital.shoppingCart.domain.Name
import com.qvest.digital.shoppingCart.domain.Product
import com.qvest.digital.shoppingCart.domain.SKU
import com.qvest.digital.shoppingCart.domain.money.Price
import io.github.oshai.kotlinlogging.KotlinLogging

class MockedAdapter : ProductPort {
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