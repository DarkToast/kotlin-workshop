package shoppingCart.application

import shoppingCart.domain.Quantity
import shoppingCart.domain.SKU
import shoppingCart.domain.ShoppingCart
import shoppingCart.domain.ShoppingCartUuid
import shoppingCart.ports.driven.database.ShoppingCartRepositoryPort
import shoppingCart.ports.driven.productService.ProductRepositoryPort
import org.springframework.stereotype.Service
import java.util.*

class ProductNotFoundException(sku: SKU): ApplicationException("The product with the sku $sku is unknown.")

@Service
class AppShoppingCartService(private val shoppingCartRepositoryPort: ShoppingCartRepositoryPort) : ShoppingCartService {

    override fun takeNewShoppingCart(): ShoppingCart {
        val shoppingCart = ShoppingCart()
        shoppingCartRepositoryPort.save(shoppingCart)
        return shoppingCart
    }

    override fun showShoppingCart(shoppingCartUuid: ShoppingCartUuid): Optional<ShoppingCart> {
        return shoppingCartRepositoryPort.load(shoppingCartUuid)
    }
}