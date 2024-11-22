@file:Suppress("unused")

package com.qvest.digital.shoppingCart.ports.database

import com.qvest.digital.shoppingCart.domain.Item
import com.qvest.digital.shoppingCart.domain.Quantity
import com.qvest.digital.shoppingCart.domain.ShoppingCart
import com.qvest.digital.shoppingCart.domain.ShoppingCartUuid
import com.qvest.digital.shoppingCart.domain.money.Price
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
    fun toDomain() = com.qvest.digital.shoppingCart.domain.Product(
        com.qvest.digital.shoppingCart.domain.SKU(sku),
        Price(price),
        com.qvest.digital.shoppingCart.domain.Name(name)
    )
}