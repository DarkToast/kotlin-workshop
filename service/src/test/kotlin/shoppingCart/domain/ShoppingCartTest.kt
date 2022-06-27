package shoppingCart.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


/*
 * Aufgabe: 5
 *
 * Bitte implementieren Sie nach und nach die einzelnen Teste der Klasse shoppingCart.domain.ShoppingCart.
 *
 * Die Teste sind mit Level 1 - 3 markiert und zeigen die steigende Komplexität der Klasse und auch der Implmenentierung
 * an.
 *
 * Einige Teste haben eine Beschreibung, die mit einem '!' beginnen. Diese sind zur Zeit inaktiv. Entfernen Sie
 * bitte das Ausrufezeichen, um den Test zu aktivieren und ihn implementieren zu können.
 *
 * Das Ziel ist, kompliziertere Methoden mit Collections und nullable fields zu implementieren.
*/
class ShoppingCartTest {
    lateinit var cart: ShoppingCart

    val sku = SKU("12345")
    val secondSku = SKU("54321")

    @BeforeEach
    @Test
    fun setUp() {
        cart = ShoppingCart()
    }

    @Test
    fun `has an uuid`() {
        assertNotNull(cart.shoppingCartUuid.uuid)
    }

    @Test
    fun ` level 1 - the new cart is empty and has no products`() {
        assertTrue(cart.isEmpty())
    }

    @Test
    fun ` level 1 - can calculate the amount of its items`() {
        val items: List<Item> = listOf(
            Item(aProduct(Price(2, 99)), Price(2, 99), Quantity(2)),
            Item(anotherProduct(Price(3, 49)), Price(3, 49), Quantity(3))
        )

        val cart = ShoppingCart(items = items)

        assertEquals(Amount(16, 45), cart.amount())
    }

    @Test
    fun ` level 1 - can be initialized with a predefined product set`() {
        val items: List<Item> = listOf(
            Item(aProduct(Price(10, 0)), Price(10, 0), Quantity(2)),
            Item(anotherProduct(Price(2, 0)), Price(2, 0), Quantity(4))
        )
        val uuid = ShoppingCartUuid()

        val cart = ShoppingCart(uuid, items)

        assertEquals(uuid, cart.shoppingCartUuid)
        assertEquals(Amount(28, 0), cart.amount())

        assertEquals(Quantity(2), cart.quantityOfProduct(sku).get())
        assertEquals(Quantity(4), cart.quantityOfProduct(secondSku).get())
    }

    @Test
    fun ` level 1 - can take items of different products`() {
        val items: MutableList<Item> = mutableListOf(
            Item(aProduct(), Price(10, 0), Quantity(5)),
            Item(anotherProduct(), Price(10, 0), Quantity(1))
        )

        val cart = ShoppingCart(items = items)

        assertEquals(5, cart.quantityOfProduct(sku).get().value)
        assertEquals(1, cart.quantityOfProduct(secondSku).get().value)
    }

    @Test
    fun ` level 2 - can put products into`() {
        cart.addProduct(aProduct(), Quantity(5))
        assertFalse(cart.isEmpty())
    }

    @Test
    fun ` level 2 - can take items to a quantity of 10`() {
        val items: MutableList<Item> = mutableListOf(
            Item(aProduct(), Price(10, 0), Quantity(10))
        )

        val cart = ShoppingCart(items = items)

        assertEquals(10, cart.quantityOfProduct(sku).get().value)
    }

    @Test
    fun ` level 2 - An exceeding quantity of a products result in an exception and has no effect on the shopping cart`() {
        cart.addProduct(aProduct(), Quantity(5))

        assertThrows<TooMuchItemsOfAProduct> {
            cart.addProduct(aProduct(), Quantity(6))
        }

        assertEquals(5, cart.quantityOfProduct(sku).get().value)
    }

    @Test
    fun ` level 3 - An exceeding total amount over 300,00 result in an exception and has no effect on the shopping cart`() {
        cart.addProduct(aProduct(Price(100, 0)), Quantity(2))
            .addProduct(anotherProduct(Price(99, 99)), Quantity(1))

        assertThrows<MaximumShoppingCardAmountExceededException> {
            cart.addProduct(aProduct(Price(100, 0)), Quantity(1))
        }

        assertEquals(Amount(299, 99), cart.amount())
    }

    @Test
    fun ` level 3 - A shopping cart can not have more than 50 different products`() {
        val cart = aCartWithFiftyProducts()

        assertThrows<MaximumProductCountExceededException> {
            cart.addProduct(Product(SKU("51"), Price(1, 0), Name("Milch")), Quantity(1))
        }
    }

    private fun aCartWithFiftyProducts(): ShoppingCart {
        (1..50).forEach { it ->
            cart.addProduct(Product(SKU("$it"), Price(1, 0), Name("Joghurt")), Quantity(1))
        }
        return cart
    }

    private fun aProduct(price: Price = Price(10, 0)): Product {
        return Product(sku, price, Name("Schokolade"))
    }

    private fun anotherProduct(price: Price = Price(10, 0)): Product {
        return Product(secondSku, price, Name("Brot"))
    }
}
