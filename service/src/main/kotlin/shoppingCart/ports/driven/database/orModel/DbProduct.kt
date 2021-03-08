package shoppingCart.ports.driven.database.orModel

import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "Product")
data class DbProduct(
    @Id
    val sku: String,
    val name: String,
    val price: Int
)