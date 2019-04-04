package de.tarent.ciwanzik.shoppingCart.ports.driven.database

import de.tarent.ciwanzik.shoppingCart.domain.ShoppingCart
import de.tarent.ciwanzik.shoppingCart.domain.ShoppingCartUuid
import de.tarent.ciwanzik.shoppingCart.ports.driven.database.orModel.DbShoppingCart
import de.tarent.ciwanzik.shoppingCart.ports.driven.database.orModel.DbShoppingCartItem
import java.util.*

open class JpaShoppingCartRepository(private val shoppingCartJPARepository: ShoppingCartJPARepository): ShoppingCartRepositoryPort {

    override fun load(shoppingCartUuid: ShoppingCartUuid): Optional<ShoppingCart> {
        val optional: Optional<DbShoppingCart> = shoppingCartJPARepository.findById(shoppingCartUuid.toString())

        return optional
            .map { dbShoppingCart ->
                val itemMap = dbShoppingCart.items!!.map { item -> item.toProductPair() }.toMap().toMutableMap()
                ShoppingCart(ShoppingCartUuid(dbShoppingCart.shoppingCartUuid!!), itemMap)
            }
    }

    override fun save(shoppingCart: ShoppingCart) {

        val cartItemList: MutableList<DbShoppingCartItem> = shoppingCart.content()
                .map { pair -> DbShoppingCartItem.fromProduct(pair.first, pair.second) }
                .toMutableList()

        val dbShoppingCart = DbShoppingCart(
                shoppingCart.shoppingCartUuid.toString(),
                shoppingCart.amount().valueInCent,
                cartItemList
        )

        shoppingCartJPARepository.save(dbShoppingCart)
    }
}