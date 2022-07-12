package shoppingCart.application

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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

class AppShoppingCartServiceTest {
    private var shoppingCartPort = Mockito.mock(ShoppingCartRepositoryPort::class.java)
    private val productPort = Mockito.mock(ProductRepositoryPort::class.java)
    private val sku = SKU("123456")

    private val uuid = ShoppingCartUuid()
    private val shoppingCart = ShoppingCart()
    private val milk = Product(sku, Price(10, 0), Name("Milch"))


    @Test
    fun `if not existing, the service returns empty`() {
        val service = AppShoppingCartService(shoppingCartPort, productPort)

        Mockito.`when`(shoppingCartPort.load(uuid)).thenReturn(Optional.empty())

        val result = service.putProductIntoShoppingCart(uuid, sku, Quantity(2))
        assertFalse(result.isPresent)
    }

    @Test
    fun `if exists the shopping cart has the product`() {
        val service = AppShoppingCartService(shoppingCartPort, productPort)

        Mockito.`when`(shoppingCartPort.load(uuid)).thenReturn(Optional.of(shoppingCart))
        Mockito.`when`(productPort.findProductBySku(sku)).thenReturn(Optional.of(milk))

        val result = service.putProductIntoShoppingCart(uuid, sku, Quantity(2))
        assertTrue(result.isPresent)

        val shoppingCart = result.get()
        assertFalse(result.get().isEmpty())
        assertEquals(Quantity(2), shoppingCart.quantityOfProduct(sku).get())

        val items: List<Item> = result.get().items()
        assertEquals(1, items.size)
        assertEquals(Item(milk, Quantity(2)), items[0])
    }

    @Test
    fun `if exists the shopping cart has no product on a unknown SKU`() {
        val service = AppShoppingCartService(shoppingCartPort, productPort)

        Mockito.`when`(shoppingCartPort.load(uuid)).thenReturn(Optional.of(shoppingCart))
        Mockito.`when`(productPort.findProductBySku(sku)).thenReturn(Optional.empty())

        assertThrows<ProductNotFoundException> { service.putProductIntoShoppingCart(uuid, sku, Quantity(2)) }
    }

    @Test
    fun `if exists the shopping cart should be saved`() {
        val service = AppShoppingCartService(shoppingCartPort, productPort)

        Mockito.`when`(shoppingCartPort.load(uuid)).thenReturn(Optional.of(shoppingCart))
        Mockito.`when`(productPort.findProductBySku(sku)).thenReturn(Optional.of(milk))

        service.putProductIntoShoppingCart(uuid, sku, Quantity(2))

        Mockito.verify(shoppingCartPort, times(1)).save(shoppingCart)
    }
}
