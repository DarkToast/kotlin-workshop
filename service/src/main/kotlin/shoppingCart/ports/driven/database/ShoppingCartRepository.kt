package shoppingCart.ports.driven.database

import mu.KotlinLogging
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import shoppingCart.domain.Item
import shoppingCart.domain.Product
import shoppingCart.domain.ShoppingCart
import shoppingCart.domain.ShoppingCartUuid
import java.util.Optional
import java.util.UUID
import javax.transaction.Transactional

@Repository
class ShoppingCartRepository(private val jpaRepo: JpaShoppingCartRepo) : ShoppingCartRepositoryPort {
    private val logger = KotlinLogging.logger {}

    @Transactional
    override fun load(shoppingCartUuid: ShoppingCartUuid): Optional<ShoppingCart> {
        logger.info { "Loading shopping cart '${shoppingCartUuid.uuid}' from database" }

        val value = jpaRepo
            .findById(shoppingCartUuid.uuid)
            .map { it.toDomain() }
        logger.info { "Found $value" }
        logger.info { "Found all ${jpaRepo.findAll()}" }

        return value

    }

    @Transactional
    override fun save(shoppingCart: ShoppingCart) {
        logger.info { "Persisting shopping cart to database" }
        jpaRepo.saveAndFlush(shoppingCart.toEntity())
    }
}

@Repository
interface JpaShoppingCartRepo : JpaRepository<ShoppingCartEntity, UUID>

private fun ShoppingCart.toEntity() = ShoppingCartEntity(
    uuid = this.shoppingCartUuid.uuid,
    items = this.items().map { it.toEntity(this.shoppingCartUuid.uuid) }
)

private fun Item.toEntity(shoppingCartId: UUID) = ItemEntity(
    sku = this.product.sku.value,
    shoppingCartId = shoppingCartId,
    effectivePrice = this.effectivePrice.valueInCent,
    quantity = this.quantity.value,
    product = this.product.toEntity()
)

private fun Product.toEntity() = ProductEntity(
    sku = this.sku.value,
    name = this.name.value,
    price = this.price.valueInCent
)