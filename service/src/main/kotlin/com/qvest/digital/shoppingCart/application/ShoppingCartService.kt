package com.qvest.digital.shoppingCart.application

import com.qvest.digital.shoppingCart.DomainException
import com.qvest.digital.shoppingCart.domain.Quantity
import com.qvest.digital.shoppingCart.domain.SKU
import com.qvest.digital.shoppingCart.domain.ShoppingCart
import com.qvest.digital.shoppingCart.domain.ShoppingCartUuid
import com.qvest.digital.shoppingCart.ports.database.RepositoryPort
import com.qvest.digital.shoppingCart.ports.product.ProductPort
import org.springframework.stereotype.Service

class ProductNotFoundException(sku: SKU) :
    DomainException("The product with the sku $sku is unknown.")

class ShoppingCartNotFoundException(uuid: ShoppingCartUuid) :
    DomainException("The shopping cart with id $uuid is unknown.")

@Service
class ShoppingCartService(private val repositoryPort: RepositoryPort, private val productPort: ProductPort) :
    ShoppingCartPort {

    override fun takeNewShoppingCart(): ShoppingCart {
        val shoppingCart = ShoppingCart()
        repositoryPort.save(shoppingCart)
        return shoppingCart
    }

    override fun showShoppingCart(shoppingCartUuid: ShoppingCartUuid): ShoppingCart? {
        return repositoryPort.load(shoppingCartUuid)
    }

    override fun putProductIntoShoppingCart(
        shoppingCartUuid: ShoppingCartUuid,
        productSku: SKU,
        quantity: Quantity
    ): ShoppingCart? {
        val shoppingCart = repositoryPort.load(shoppingCartUuid) ?: throw ShoppingCartNotFoundException(shoppingCartUuid)
        val product = productPort.findProductBySku(productSku) ?: throw ProductNotFoundException(productSku)

        shoppingCart.addProduct(product, quantity)
        repositoryPort.save(shoppingCart)

        return shoppingCart
    }
}