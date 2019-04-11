package shoppingCart.ports.driven.database.orModel


import javax.persistence.CascadeType.ALL
import javax.persistence.Entity
import javax.persistence.FetchType.EAGER
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity(name = "ShoppingCart")
data class DbShoppingCart (
    @Id
    var shoppingCartUuid: String?,

    var amount: Int?,

    @OneToMany(fetch = EAGER, cascade = [ALL])
    var items: MutableList<DbShoppingCartItem>?
)