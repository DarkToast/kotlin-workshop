package spring.product

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface ProductRepo: MongoRepository<Product, UUID> {
    fun findFirstBySku(sku: SKU): Product?
}