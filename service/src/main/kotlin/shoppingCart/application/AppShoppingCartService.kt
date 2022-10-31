package shoppingCart.application

import org.springframework.stereotype.Service
import shoppingCart.domain.Quantity
import shoppingCart.domain.SKU
import shoppingCart.domain.ShoppingCart
import shoppingCart.domain.ShoppingCartUuid
import shoppingCart.ports.driven.database.ShoppingCartRepositoryPort
import shoppingCart.ports.driven.productService.ProductRepositoryPort
import java.util.Optional

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