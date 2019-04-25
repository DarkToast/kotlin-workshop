package shoppingCart.application

import org.springframework.stereotype.Service
import shoppingCart.domain.Quantity
import shoppingCart.domain.SKU
import shoppingCart.domain.ShoppingCart
import shoppingCart.domain.ShoppingCartUuid
import shoppingCart.ports.driven.database.ShoppingCartRepositoryPort
import shoppingCart.ports.driven.productService.ProductRepositoryPort
import java.util.Optional

class ProductNotFoundException(sku: SKU): ApplicationException("The product with the sku $sku is unknown.")

@Service
class AppShoppingCartService(
        private val shoppingCartRepositoryPort: ShoppingCartRepositoryPort,
        private val productRepositoryPort: ProductRepositoryPort) : ShoppingCartService {

    override fun putProductIntoShoppingCart(shoppingCartUuid: ShoppingCartUuid, productSku: SKU, quantity: Quantity): Optional<ShoppingCart> {
        return shoppingCartRepositoryPort.load(shoppingCartUuid).map { shoppingCart ->
            productRepositoryPort.findProductBySku(productSku)
                    .map { foundProduct ->
                        shoppingCart.putProductInto(foundProduct, quantity)
                    }
                    .orElseThrow {
                        ProductNotFoundException(productSku)
                    }
        }.map { shoppingCart ->
            shoppingCartRepositoryPort.save(shoppingCart)
            shoppingCart
        }
    }

    override fun takeNewShoppingCart(): ShoppingCart {
        val shoppingCart = ShoppingCart()
        shoppingCartRepositoryPort.save(shoppingCart)
        return shoppingCart
    }

    override fun showShoppingCart(shoppingCartUuid: ShoppingCartUuid): Optional<ShoppingCart> {
        return shoppingCartRepositoryPort.load(shoppingCartUuid)
    }
}