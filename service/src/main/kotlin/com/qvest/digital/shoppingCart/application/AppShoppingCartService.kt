package com.qvest.digital.shoppingCart.application

import com.qvest.digital.shoppingCart.domain.Quantity
import com.qvest.digital.shoppingCart.domain.SKU
import com.qvest.digital.shoppingCart.domain.ShoppingCart
import com.qvest.digital.shoppingCart.domain.ShoppingCartUuid
import com.qvest.digital.shoppingCart.ports.database.ShoppingCartRepositoryPort
import com.qvest.digital.shoppingCart.ports.productService.ProductRepositoryPort
import org.springframework.stereotype.Service

class ProductNotFoundException(sku: SKU) : ApplicationException("The product with the sku $sku is unknown.")

@Service
class AppShoppingCartService(
    private val shoppingCartRepositoryPort: ShoppingCartRepositoryPort,
    private val productRepositoryPort: ProductRepositoryPort
) : ShoppingCartService {

    override fun putProductIntoShoppingCart(
        shoppingCartUuid: ShoppingCartUuid,
        productSku: SKU,
        quantity: Quantity
    ): ShoppingCart? {
        val shoppingCart = shoppingCartRepositoryPort.load(shoppingCartUuid) ?: return null
        val product = productRepositoryPort.findProductBySku(productSku) ?: throw ProductNotFoundException(productSku)

        shoppingCart.addProduct(product, quantity)
        shoppingCartRepositoryPort.save(shoppingCart)

        return shoppingCart
    }

    override fun takeNewShoppingCart(): ShoppingCart {
        val shoppingCart = ShoppingCart()
        shoppingCartRepositoryPort.save(shoppingCart)
        return shoppingCart
    }

    override fun showShoppingCart(shoppingCartUuid: ShoppingCartUuid): ShoppingCart? {
        return shoppingCartRepositoryPort.load(shoppingCartUuid)
    }
}