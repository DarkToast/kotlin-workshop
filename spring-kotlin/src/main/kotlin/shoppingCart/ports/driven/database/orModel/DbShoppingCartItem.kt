package shoppingCart.ports.driven.database.orModel

import shoppingCart.domain.Name
import shoppingCart.domain.Price
import shoppingCart.domain.Product
import shoppingCart.domain.Quantity
import shoppingCart.domain.SKU
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.CascadeType.DETACH
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.CascadeType.REMOVE
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity(name = "ShoppingCartItem")
data class DbShoppingCartItem(
        @Id
        val id: UUID = UUID.randomUUID(),

        val sku: String,

        @OneToOne(cascade = [PERSIST, REFRESH, DETACH, MERGE])
        val product: DbProduct,

        val quantity: Int
) {

    fun toProductPair(): Pair<Product, Quantity> {
        val euro = product.price / 100
        val cent = product.price % 100
        val domainProduct = Product(SKU(sku), Price(euro, cent), Name(product.name))
        val quantity = Quantity(quantity)

        return Pair(domainProduct, quantity)
    }

    companion object {
        fun fromProduct(product: Product, quantity: Quantity): DbShoppingCartItem {
            val dbProduct = DbProduct(product.sku.toString(), product.name.toString(), product.price.valueInCent)
            return DbShoppingCartItem(sku = product.sku.toString(), product = dbProduct, quantity = quantity.value)
        }
    }
}