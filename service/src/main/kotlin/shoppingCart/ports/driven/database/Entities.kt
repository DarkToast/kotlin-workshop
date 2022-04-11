package shoppingCart.ports.driven.database

import org.hibernate.annotations.Type
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
import javax.persistence.CascadeType
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity(name = "shopping_cart")
data class ShoppingCartEntity(
    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    val uuid: UUID,

    @OneToMany(cascade = [CascadeType.ALL])
    val items: List<ItemEntity>
) {
    fun toDomain(): ShoppingCart = ShoppingCart(ShoppingCartUuid(uuid), items.map { it.toDomain() })
}

class ItemPK(val sku: String?, val shoppingCartId: UUID?) : Serializable {
    constructor() : this(null, null)
}

@Entity(name = "item")
@IdClass(ItemPK::class)
data class ItemEntity(
    @Id
    val sku: String,
    @Id
    val shoppingCartId: UUID,
    val effectivePrice: Int,
    val quantity: Int,
    @OneToOne(cascade = [PERSIST, MERGE])
    val product: ProductEntity
) {
    fun toDomain() = Item(product.toDomain(), Price(effectivePrice), Quantity(quantity))
}

@Entity(name = "product")
data class ProductEntity(
    @Id
    val sku: String,
    val name: String,
    val price: Int
) {
    fun toDomain() = Product(SKU(sku), Price(price), Name(name))
}