package shoppingCart.ports.driven.database

import shoppingCart.ports.driven.database.orModel.DbShoppingCart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ShoppingCartJPARepository : JpaRepository<DbShoppingCart, String>