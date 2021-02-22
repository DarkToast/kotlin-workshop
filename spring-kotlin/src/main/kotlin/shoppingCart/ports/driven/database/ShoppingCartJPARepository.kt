package shoppingCart.ports.driven.database

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import shoppingCart.ports.driven.database.orModel.DbShoppingCart

@Repository
interface ShoppingCartJPARepository : JpaRepository<DbShoppingCart, String>