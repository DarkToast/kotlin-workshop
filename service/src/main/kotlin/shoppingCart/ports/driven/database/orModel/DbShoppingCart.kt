package shoppingCart.ports.driven.database.orModel


import java.util.UUID
import javax.persistence.CascadeType.ALL
import javax.persistence.Entity
import javax.persistence.FetchType.EAGER
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity(name = "ShoppingCart")
data class DbShoppingCart (
    @Id
    val shoppingCartUuid: UUID,

    val amount: Int,

    @OneToMany(fetch = EAGER, cascade = [ALL])
    val items: List<DbShoppingCartItem>
)