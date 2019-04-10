package de.tarent.ciwanzik.shoppingCart.application

import de.tarent.ciwanzik.shoppingCart.domain.Quantity
import de.tarent.ciwanzik.shoppingCart.domain.SKU
import de.tarent.ciwanzik.shoppingCart.domain.ShoppingCart
import de.tarent.ciwanzik.shoppingCart.domain.ShoppingCartUuid
import de.tarent.ciwanzik.shoppingCart.ports.driven.database.ShoppingCartRepositoryPort
import de.tarent.ciwanzik.shoppingCart.ports.driven.productService.ProductRepositoryPort
import org.springframework.stereotype.Service
import java.util.*

@Service
class AppShoppingCartService(private val shoppingCartRepositoryPort: ShoppingCartRepositoryPort) : ShoppingCartService {

    override fun putProductIntoShoppingCart(shoppingCartUuid: ShoppingCartUuid, productSku: SKU, quantity: Quantity): Optional<ShoppingCart> {
       return Optional.empty()
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