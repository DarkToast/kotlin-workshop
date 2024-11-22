package com.qvest.digital.shoppingCart.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.qvest.digital.shoppingCart.domain.money.Amount
import com.qvest.digital.shoppingCart.domain.money.MaximumShoppingCardAmountExceededException
import com.qvest.digital.shoppingCart.domain.money.Price


/*
 * Aufgabe: 5
 *
 * Entfernen Sie bitte die @Disabled annotation von der hier drunter liegenden Klasse und implementieren Sie nach
 * und nach die einzelnen Teste der Klasse shoppingCart.domain.ShoppingCart.
 *
 * Die Teste sind mit Level 1 - 3 markiert und zeigen die steigende Komplexität der Klasse und auch der Implmenentierung
 * an. Denken Sie sich bitte bei jedem Level ein paar zusätzliche Tests aus um die funktionale Abdeckung noch zu
 * erhöhen.
 *
 * Das Ziel ist, kompliziertere Methoden mit Collections und nullable fields zu implementieren.
 *
 * Folgende Anforderungen sollen umgesetzt sein:
 *
 * level 1
 * - Ein ShoppingCart hat eine UUID.
 * - Ein ShoppingCart gibt über `isEmpty` Auskunft, ob er leer ist.
 * - Ein ShoppingCart kann mit einer UUID und einer Liste an Produkten initialisiert werden.
 * - Ein ShoppingCart kann per `amount` seinen Wert berechnen.
 *
 * level 2
 * - Per `addProduct` kann ein Produkt mit Anzahl dem ShoppingCart hinzugefügt werden.
 * - Ein ShoppingCart kann per `quantityOfProduct` Die Anzahl eines Produkts zurückgeben.
 * - Die Menge eines Produkts kann 10 nicht überschreiten.                  -> TooMuchItemsOfAProduct
 *
 * level 3
 * - Ein ShoppingCart kann nicht mehr als 300,00€ wert sein.                -> MaximumShoppingCardAmountExceededException
 * - Ein ShoppingCart kann nicht mehr als 50 verschiedene Produkte haben.   -> MaximumProductCountExceededException
*/
class ShoppingCartTest {
    private lateinit var cart: ShoppingCart

    private val sku = SKU("12345")
    private val secondSku = SKU("54321")

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
    fun `level 1 - the new cart is empty and has no products`() {
        assertTrue(cart.isEmpty())
    }

    @Test
    fun `level 1 - can calculate the amount of its items`() {
        val items: List<Item> = listOf(
            Item(aProduct(Price(2, 99)), Quantity(2)),
            Item(anotherProduct(Price(3, 49)), Quantity(3))
        )

        val cart = ShoppingCart(items = items)

        assertEquals(Amount(16, 45), cart.amount())
    }

    @Test
    fun ` level 1 - can be initialized with a predefined product set`() {
        val items: List<Item> = listOf(
            Item(aProduct(Price(10, 0)), Quantity(2)),
            Item(anotherProduct(Price(2, 0)), Quantity(4))
        )
        val uuid = ShoppingCartUuid()

        val cart = ShoppingCart(uuid, items)

        assertEquals(uuid, cart.shoppingCartUuid)
        assertEquals(Amount(28, 0), cart.amount())

        assertEquals(Quantity(2), cart.quantityOfProduct(sku).get())
        assertEquals(Quantity(4), cart.quantityOfProduct(secondSku).get())
    }

    @Test
    fun `level 2 - can take items of different products`() {
        val items: MutableList<Item> = mutableListOf(
            Item(aProduct(), Quantity(5)),
            Item(anotherProduct(), Quantity(1))
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
            Item(aProduct(), Quantity(10))
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
            cart.addProduct(
                Product(
                    SKU("51"),
                    Price(1, 0),
                    Name("Milch")
                ), Quantity(1))
        }
    }

    private fun aCartWithFiftyProducts(): ShoppingCart {
        (1..50).forEach {
            cart.addProduct(
                Product(
                    SKU("$it"),
                    Price(1, 0),
                    Name("Joghurt")
                ), Quantity(1))
        }
        return cart
    }

    private fun aProduct(price: Price = Price(10, 0)): Product {
        return Product(
            sku,
            price,
            Name("Schokolade")
        )
    }

    private fun anotherProduct(price: Price = Price(10, 0)): Product {
        return Product(
            secondSku,
            price,
            Name("Brot")
        )
    }
}
