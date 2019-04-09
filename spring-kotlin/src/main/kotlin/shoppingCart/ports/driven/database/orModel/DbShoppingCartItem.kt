package de.tarent.ciwanzik.shoppingCart.ports.driven.database.orModel

import de.tarent.ciwanzik.shoppingCart.domain.*
import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity(name = "ShoppingCartItem")
data class DbShoppingCartItem(
        @Id
        var id: UUID = UUID.randomUUID(),

        var sku: String?,

        @OneToOne(cascade = [CascadeType.ALL])
        var product: DbProduct?,

        var quantity: Int?
) {

    fun toProductPair(): Pair<Product, Quantity> {
        if(sku == null || product == null || quantity == null) {
            throw IllegalStateException("DbShoppingCartItem has null values!")
        }

        val euro = product!!.price!! / 100
        val cent = product!!.price!! % 100
        val domainProduct = Product(SKU(sku!!), Price(euro, cent), Name(product!!.name!!))
        val quantity = Quantity(quantity!!)

        return Pair(domainProduct, quantity)
    }

    companion object {
        fun fromProduct(product: Product, quantity: Quantity): DbShoppingCartItem {
            val dbProduct = DbProduct(product.sku.toString(), product.name.toString(), product.price.valueInCent)
            return DbShoppingCartItem(sku = product.sku.toString(), product = dbProduct, quantity = quantity.value)
        }
    }
}