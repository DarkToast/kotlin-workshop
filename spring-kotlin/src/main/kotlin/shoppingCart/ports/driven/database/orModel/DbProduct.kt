package shoppingCart.ports.driven.database.orModel

import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "Product")
data class DbProduct(
    @Id
    var sku: String?,
    var name: String?,
    var price: Int?
)