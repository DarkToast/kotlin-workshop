package shoppingCart.application

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
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

        Mockito.`when`(shoppingCartPort.load(uuid)).thenReturn(null)

        val shoppingCart = service.putProductIntoShoppingCart(uuid, sku, Quantity(2))
        assertNull(shoppingCart)
    }

    @Test
    fun `if exists the shopping cart has the product`() {
        val service = AppShoppingCartService(shoppingCartPort, productPort)

        Mockito.`when`(shoppingCartPort.load(uuid)).thenReturn(shoppingCart)
        Mockito.`when`(productPort.findProductBySku(sku)).thenReturn(milk)

        val shoppingCart = service.putProductIntoShoppingCart(uuid, sku, Quantity(2))
        assertNotNull(shoppingCart)
        assertFalse(shoppingCart!!.isEmpty())
        assertEquals(Quantity(2), shoppingCart.quantityOfProduct(sku).get())

        val items: List<Item> = shoppingCart.items()
        assertEquals(1, items.size)
        assertEquals(Item(milk, Quantity(2)), items[0])
    }

    @Test
    fun `if exists the shopping cart has no product on a unknown SKU`() {
        val service = AppShoppingCartService(shoppingCartPort, productPort)

        Mockito.`when`(shoppingCartPort.load(uuid)).thenReturn(shoppingCart)
        Mockito.`when`(productPort.findProductBySku(sku)).thenReturn(null)

        assertThrows<ProductNotFoundException> { service.putProductIntoShoppingCart(uuid, sku, Quantity(2)) }
    }

    @Test
    fun `if exists the shopping cart should be saved`() {
        val service = AppShoppingCartService(shoppingCartPort, productPort)

        Mockito.`when`(shoppingCartPort.load(uuid)).thenReturn(shoppingCart)
        Mockito.`when`(productPort.findProductBySku(sku)).thenReturn(milk)

        service.putProductIntoShoppingCart(uuid, sku, Quantity(2))

        Mockito.verify(shoppingCartPort, times(1)).save(shoppingCart)
    }
}
