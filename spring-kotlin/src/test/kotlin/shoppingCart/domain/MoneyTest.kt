package shoppingCart.domain

import de.tarent.ciwanzik.shoppingCart.domain.Price
import de.tarent.ciwanzik.shoppingCart.domain.ShoppingCartAmount
import de.tarent.ciwanzik.shoppingCart.domain.TooHighPriceException
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotThrow
import io.kotlintest.shouldThrow
import io.kotlintest.specs.FeatureSpec

class MoneyTest: FeatureSpec({
    feature("the price") {
        scenario("could not be negative") {
            shouldThrow<IllegalArgumentException> {
                Price(10, -1)
            }
            shouldThrow<IllegalArgumentException> {
                Price(-1, 10)
            }
            shouldThrow<IllegalArgumentException> {
                Price(-1, -1)
            }
        }

        scenario("Cent can not exceed 99") {
            shouldThrow<IllegalArgumentException> {
                Price(10, 100)
            }
        }

        scenario("A price can not exceed 120,00") {
            shouldThrow<TooHighPriceException> {
                Price(120, 1)
            }
        }

        scenario("can be zero") {
            shouldNotThrow<Throwable> {
                Price(0, 0)
            }
        }

        scenario("can be 120,00 €") {
            shouldNotThrow<Throwable> {
                Price(120, 0)
            }
        }

        scenario("can be 0,01 €") {
            shouldNotThrow<Throwable> {
                Price(0, 1)
            }
        }

        scenario("a price can be added to another") {
            Price(10,99).plus(Price(2,89)).valueInCent shouldBe 1388
            Price(0,99).plus(Price(0,99)).valueInCent shouldBe 198
            Price(111, 59).plus(Price(0 ,41)).valueInCent shouldBe 11200
        }

        scenario("a zero price added to another does not have any effect") {
            Price(10,99).plus(Price(0, 0)).valueInCent shouldBe 1099
        }

        scenario("two prices can not exceed 120,00 €") {
            shouldThrow<TooHighPriceException> {
                Price(50, 99).plus(Price(69, 2))
            }
        }
    }

    feature("An amount") {
        scenario("Two amounts can exceed 120,00 €") {
            shouldNotThrow<Throwable> {
                ShoppingCartAmount(50, 99).plus(ShoppingCartAmount(69, 2))
            }
        }

        scenario("A single amounts can exceed 120,00 €") {
            shouldNotThrow<Throwable> {
                ShoppingCartAmount(120, 1)
            }
        }
    }
})
