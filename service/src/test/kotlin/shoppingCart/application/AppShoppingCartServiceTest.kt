package shoppingCart.application

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.mockito.Mockito
import org.mockito.Mockito.times
import shoppingCart.domain.Item
import shoppingCart.domain.Name
import shoppingCart.domain.Price
import shoppingCart.domain.Product
import shoppingCart.domain.Quantity
import shoppingCart.domain.SKU
import shoppingCart.domain.ShoppingCart
import shoppingCart.domain.ShoppingCartUuid
import shoppingCart.ports.driven.database.ShoppingCartRepositoryPort
import shoppingCart.ports.driven.productService.ProductRepositoryPort
import java.util.Optional

class AppShoppingCartServiceTest : FeatureSpec() {
    private var shoppingCartPort = Mockito.mock(ShoppingCartRepositoryPort::class.java)
    private val productPort = Mockito.mock(ProductRepositoryPort::class.java)
    private val sku = SKU("123456")

    private val uuid = ShoppingCartUuid()
    private val shoppingCart = ShoppingCart()
    private val milk = Product(sku, Price(10, 0), Name("Milch"))


    override suspend fun beforeTest(testCase: TestCase) {
        shoppingCartPort = Mockito.mock(ShoppingCartRepositoryPort::class.java)
    }

    init {
        feature("The shopping cart service") {
            scenario("if not existing, the service returns empty") {
                val service = AppShoppingCartService(shoppingCartPort, productPort)

                Mockito.`when`(shoppingCartPort.load(uuid)).thenReturn(Optional.empty())

                val result = service.putProductIntoShoppingCart(uuid, sku, Quantity(2))
                result.isPresent shouldBe false
            }

            scenario("if exists the shopping cart has the product") {
                val service = AppShoppingCartService(shoppingCartPort, productPort)

                Mockito.`when`(shoppingCartPort.load(uuid)).thenReturn(Optional.of(shoppingCart))
                Mockito.`when`(productPort.findProductBySku(sku)).thenReturn(Optional.of(milk))

                val result = service.putProductIntoShoppingCart(uuid, sku, Quantity(2))

                result.isPresent shouldBe true
                result.get().isEmpty() shouldBe false
                result.get().quantityOfProduct(sku).get() shouldBe Quantity(2)
                result.get().items() shouldContain Item(milk, Price(10, 0), Quantity(2))
            }

            scenario("if exists the shopping cart has no product on a unknown SKU") {
                val service = AppShoppingCartService(shoppingCartPort, productPort)

                Mockito.`when`(shoppingCartPort.load(uuid)).thenReturn(Optional.of(shoppingCart))
                Mockito.`when`(productPort.findProductBySku(sku)).thenReturn(Optional.empty())

                shouldThrow<ProductNotFoundException> { service.putProductIntoShoppingCart(uuid, sku, Quantity(2)) }
            }

            scenario("if exists the shopping cart should be saved") {
                val service = AppShoppingCartService(shoppingCartPort, productPort)

                Mockito.`when`(shoppingCartPort.load(uuid)).thenReturn(Optional.of(shoppingCart))
                Mockito.`when`(productPort.findProductBySku(sku)).thenReturn(Optional.of(milk))

                service.putProductIntoShoppingCart(uuid, sku, Quantity(2))

                Mockito.verify(shoppingCartPort, times(1)).save(shoppingCart)
            }
        }
    }
}