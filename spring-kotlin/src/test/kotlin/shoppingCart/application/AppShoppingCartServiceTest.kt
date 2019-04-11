package shoppingCart.application

import de.tarent.ciwanzik.shoppingCart.application.AppShoppingCartService
import de.tarent.ciwanzik.shoppingCart.application.ProductNotFoundException
import de.tarent.ciwanzik.shoppingCart.domain.*
import de.tarent.ciwanzik.shoppingCart.ports.driven.database.ShoppingCartRepositoryPort
import de.tarent.ciwanzik.shoppingCart.ports.driven.productService.ProductRepositoryPort
import io.kotlintest.TestCase
import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.FeatureSpec
import org.mockito.Mockito
import org.mockito.Mockito.times
import java.util.*


class AppShoppingCartServiceTest: FeatureSpec() {
    private var shoppingCartPort = Mockito.mock(ShoppingCartRepositoryPort::class.java)
    private val productPort = Mockito.mock(ProductRepositoryPort::class.java)
    private val sku = SKU("123456")

    private val uuid = ShoppingCartUuid()
    private val shoppingCart = ShoppingCart()
    private val milk = Product(sku, Price(10,0), Name("Milch"))

    override fun beforeTest(testCase: TestCase) {
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
                result.get().content() shouldContain Pair(milk, Quantity(2))
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