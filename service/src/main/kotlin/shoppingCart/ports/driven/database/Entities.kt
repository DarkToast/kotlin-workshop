@file:Suppress("unused")

package shoppingCart.ports.driven.database

import jakarta.persistence.CascadeType
import jakarta.persistence.CascadeType.MERGE
import jakarta.persistence.CascadeType.PERSIST
import jakarta.persistence.CascadeType.REFRESH
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import shoppingCart.domain.Item
import shoppingCart.domain.Name
import shoppingCart.domain.Price
import shoppingCart.domain.Product
import shoppingCart.domain.Quantity
import shoppingCart.domain.SKU
import shoppingCart.domain.ShoppingCart
import shoppingCart.domain.ShoppingCartUuid
import java.io.Serializable
import java.util.UUID

@Entity(name = "shopping_cart")
class ShoppingCartEntity(
    @Id
    @Column
    val uuid: UUID,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "shoppingCartId")
    val items: List<ItemEntity>
) {
    fun toDomain(): ShoppingCart = ShoppingCart(ShoppingCartUuid(uuid), items.map { it.toDomain() })
}

class ItemPK(val sku: String?, val shoppingCartId: UUID?) : Serializable {
    constructor() : this(null, null)
}

@Entity(name = "item")
@IdClass(ItemPK::class)
class ItemEntity(
    @Id
    val sku: String,
    @Id
    @Column
    val shoppingCartId: UUID,
    val effectivePrice: Int,
    val quantity: Int,
    @OneToOne(cascade = [PERSIST, MERGE, REFRESH])
    @JoinColumn(name = "sku", insertable = false, updatable = false)
    val product: ProductEntity
) {
    fun toDomain() = Item(product.toDomain(), Quantity(quantity))
}

@Entity(name = "product")
class ProductEntity(
    @Id
    val sku: String,
    val name: String,
    val price: Int
) {
    fun toDomain() = Product(SKU(sku), Price(price / 100, price % 100), Name(name))
}