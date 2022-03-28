package shoppingCart.ports.driven.database

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import io.kotest.extensions.spring.SpringExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import shoppingCart.Application
import shoppingCart.ports.driven.database.orModel.DbProduct
import shoppingCart.ports.driven.database.orModel.DbShoppingCart
import shoppingCart.ports.driven.database.orModel.DbShoppingCartItem
import java.util.UUID

@Repository
interface ProductJPARepository : JpaRepository<DbProduct, String>

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
class JpaShoppingCartRepositorySpec : FeatureSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var repository: ShoppingCartJPARepository

    @Autowired
    lateinit var productRepository: ProductJPARepository

    private val cartId = UUID.randomUUID()
    private val sku = "1234567890"

    private val product = DbProduct("1234567890", "Milch", 199)
    private val item1 = DbShoppingCartItem(UUID.randomUUID(), "1234567890", product, 2)
    private val item2 = DbShoppingCartItem(UUID.randomUUID(), "1234567890", product, 5)
    private val cart = DbShoppingCart(cartId, 499, listOf(item1, item2))

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        productRepository.deleteAll()
        repository.deleteAll()
    }

    init {
        feature("repository") {
            scenario("Can save") {
                repository.saveAndFlush(cart)
            }

            scenario("Can load") {
                repository.saveAndFlush(cart)

                val element = repository.findById(cartId)
                element.isPresent shouldBe true
            }

            scenario("does not removes products") {
                repository.saveAndFlush(cart)

                repository.deleteById(cartId)

                val element = productRepository.findById(sku)
                element.isPresent shouldBe true
            }
        }

    }

}