package shoppingCart.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe


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
class ShoppingCartTest : FeatureSpec() {
    var cart: ShoppingCart = ShoppingCart()

    val sku = SKU("12345")
    val secondSku = SKU("54321")

    override suspend fun beforeTest(testCase: TestCase) {
        cart = ShoppingCart()
    }

    init {
        feature("A shopping cart") {
            scenario("has an uuid") {
                cart.shoppingCartUuid.uuid.shouldNotBeNull()
            }

            scenario(" level 1 - the new cart is empty and has no products") {
                cart.isEmpty().shouldBe(true)
            }

            scenario(" level 1 - can calculate the amount of its items") {
                val products: MutableMap<Product, Quantity> = mutableMapOf(
                    aProduct(Price(2, 99)) to Quantity(2),
                    anotherProduct(Price(3, 49)) to Quantity(3)
                )

                val cart = ShoppingCart(cartItems = products)

                cart.amount() shouldBe ShoppingCartAmount(16, 45)
            }

            scenario(" level 1 - can be initialized with a predefined product set") {
                val products: MutableMap<Product, Quantity> = mutableMapOf(
                    aProduct(Price(10, 0)) to Quantity(2),
                    anotherProduct(Price(2, 0)) to Quantity(4)
                )
                val uuid = ShoppingCartUuid()

                val cart = ShoppingCart(uuid, products)

                cart.shoppingCartUuid.shouldBe(uuid)
                cart.amount().shouldBe(ShoppingCartAmount(28, 0))

                cart.quantityOfProduct(sku).get().shouldBe(Quantity(2))
                cart.quantityOfProduct(secondSku).get().shouldBe(Quantity(4))
            }

            scenario(" level 1 - can take items of different products") {
                val products: MutableMap<Product, Quantity> = mutableMapOf(
                    aProduct() to Quantity(5),
                    anotherProduct() to Quantity(1)
                )

                val cart = ShoppingCart(cartItems = products)

                cart.quantityOfProduct(sku).get().value.shouldBe(5)
                cart.quantityOfProduct(secondSku).get().value.shouldBe(1)
            }

            scenario(" level 2 - can put products into") {
                cart.putProductInto(aProduct(), Quantity(5))
                cart.isEmpty().shouldBeFalse()
            }

            scenario(" level 2 - can take items to a quantity of 10") {
                val products: MutableMap<Product, Quantity> = mutableMapOf(aProduct() to Quantity(10))
                val cart = ShoppingCart(cartItems = products)

                cart.quantityOfProduct(sku).get().value.shouldBe(10)
            }

            scenario(" level 2 - An exceeding quantity of a products result in an exception and has no effect on the shopping cart") {
                cart.putProductInto(aProduct(), Quantity(5))

                shouldThrow<TooMuchItemsOfAProduct> {
                    cart.putProductInto(aProduct(), Quantity(6))
                }

                cart.quantityOfProduct(sku).get().value.shouldBe(5)
            }

            scenario(" level 3 - An exceeding total amount over 300,00 result in an exception and has no effect on the shopping cart") {
                cart.putProductInto(aProduct(Price(100, 0)), Quantity(2))
                    .putProductInto(anotherProduct(Price(99, 99)), Quantity(1))

                shouldThrow<MaximumShoppingCardAmountExceededException> {
                    cart.putProductInto(aProduct(Price(100, 0)), Quantity(1))
                }

                cart.amount() shouldBe ShoppingCartAmount(299, 99)
            }

            scenario(" level 3 - A shopping cart can not have more than 50 different products") {
                val cart = aCartWithFiftyProducts()

                shouldThrow<MaximumProductCountExceededException> {
                    cart.putProductInto(Product(SKU("51"), Price(1, 0), Name("Milch")), Quantity(1))
                }
            }
        }
    }

    private fun aCartWithFiftyProducts(): ShoppingCart {
        (1..50).forEach { it ->
            cart.putProductInto(Product(SKU("$it"), Price(1, 0), Name("Joghurt")), Quantity(1))
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
