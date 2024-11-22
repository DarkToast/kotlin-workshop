package com.qvest.digital.shoppingCart.ports.database

import com.qvest.digital.shoppingCart.domain.Item
import com.qvest.digital.shoppingCart.domain.ShoppingCart
import com.qvest.digital.shoppingCart.domain.ShoppingCartUuid
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
class ShoppingCartRepository(private val jpaRepo: JpaShoppingCartRepo) : ShoppingCartRepositoryPort {
    private val logger = KotlinLogging.logger {}

    @Transactional
    override fun load(shoppingCartUuid: ShoppingCartUuid): ShoppingCart? {
        logger.info { "Loading shopping cart '${shoppingCartUuid.uuid}' from database" }

        val value = jpaRepo.findByIdOrNull(shoppingCartUuid.uuid)?.toDomain()

        if (value != null) {
            logger.info { "Found $value" }
        }

        return value

    }

    @Transactional
    override fun save(shoppingCart: ShoppingCart) {
        logger.info { "Persisting shopping cart to database" }
        jpaRepo.save(shoppingCart.toEntity())
    }
}

@Repository
interface JpaShoppingCartRepo : CrudRepository<ShoppingCartEntity, UUID>

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

private fun com.qvest.digital.shoppingCart.domain.Product.toEntity() = ProductEntity(
    sku = this.sku.value,
    name = this.name.value,
    price = this.price.valueInCent
)