package com.qvest.digital.shoppingCart.ports.database

import com.qvest.digital.shoppingCart.domain.Item
import com.qvest.digital.shoppingCart.domain.Product
import com.qvest.digital.shoppingCart.domain.ShoppingCart
import com.qvest.digital.shoppingCart.domain.ShoppingCartUuid
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
interface JpaShoppingCartRepo : CrudRepository<ShoppingCartEntity, UUID>

@Repository
class Repository(private val jpaRepo: JpaShoppingCartRepo) : RepositoryPort {
    private val logger = KotlinLogging.logger {}

    @Transactional
    override fun load(shoppingCartUuid: ShoppingCartUuid): ShoppingCart? {
        logger.info { "Loading shopping cart '${shoppingCartUuid.uuid}' from database" }
        return jpaRepo
            .findByIdOrNull(shoppingCartUuid.uuid)
            ?.toDomain()
            ?.also {
                logger.info { "Found shopping cart '${shoppingCartUuid.uuid}'" }
            }
    }

    @Transactional
    override fun save(shoppingCart: ShoppingCart) {
        logger.info { "Persisting shopping cart to database" }
        jpaRepo.save(shoppingCart.toEntity())
    }
}

private fun ShoppingCart.toEntity() = ShoppingCartEntity(
    uuid = this.shoppingCartUuid.uuid,
    items = this.items().map { it.toEntity(this.shoppingCartUuid.uuid) }
)

private fun Item.toEntity(shoppingCartId: UUID) = ItemEntity(
    sku = this.product.sku.value,
    shoppingCartId = shoppingCartId,
    effectivePrice = this.product.price.valueInCent,
    quantity = this.quantity.value,
    product = this.product.toEntity()
)

private fun Product.toEntity() = ProductEntity(
    sku = this.sku.value,
    name = this.name.value,
    price = this.price.valueInCent
)