package de.tarent.ciwanzik.shoppingCart.ports.driven.database

import de.tarent.ciwanzik.shoppingCart.ports.driven.database.orModel.DbShoppingCart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ShoppingCartJPARepository : JpaRepository<DbShoppingCart, String>