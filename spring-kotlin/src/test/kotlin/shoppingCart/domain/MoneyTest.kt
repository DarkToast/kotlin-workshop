package shoppingCart.domain

import de.tarent.ciwanzik.shoppingCart.domain.*
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotThrow
import io.kotlintest.shouldThrow
import io.kotlintest.specs.FeatureSpec

class MoneyTest: FeatureSpec({
    feature("the price") {
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

        scenario("!has a value in cent") {
            Price(10, 11).valueInCent shouldBe 1011
        }

        scenario("!can not be negative") {
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

        scenario("!Cent can not exceed 99 cents") {
            shouldThrow<IllegalArgumentException> {
                Price(10, 100)
            }
        }

        scenario("!A price can not exceed 120,00") {
            shouldThrow<TooHighPriceException> {
                Price(120, 1)
            }
        }

        scenario("!a price can be added to another") {
            Price(10,99).plus(Price(2,89)).valueInCent shouldBe 1388
            Price(0,99).plus(Price(0,99)).valueInCent shouldBe 198
            Price(111, 59).plus(Price(0 ,41)).valueInCent shouldBe 11200
        }

        scenario("!a zero price added to another does not have any effect") {
            Price(10,99).plus(Price(0, 0)).valueInCent shouldBe 1099
        }

        scenario("!two combined prices can not exceed 120,00 €") {
            shouldThrow<TooHighPriceException> {
                Price(50, 99).plus(Price(69, 2))
            }
        }

        scenario("!can be multiply with a quantity") {
            Price(10, 0).times(Quantity(2)).valueInCent shouldBe 2000
        }

        scenario("!can be multiply with a quantity 0") {
            Price(10, 0).times(Quantity(0)).valueInCent shouldBe 0
        }

        scenario("!can be multiply with a quantity 1") {
            Price(10, 0).times(Quantity(1)).valueInCent shouldBe 1000
        }
    }

    feature("An amount") {
        scenario("can be zero") {
            shouldNotThrow<Throwable> {
                ShoppingCartAmount(0, 0)
            }
        }

        scenario("can be 300,00 €") {
            shouldNotThrow<Throwable> {
                ShoppingCartAmount(300, 0)
            }
        }

        scenario("can be 0,01 €") {
            shouldNotThrow<Throwable> {
                ShoppingCartAmount(0, 1)
            }
        }

        scenario("!has a value in cent") {
            ShoppingCartAmount(10, 11).valueInCent shouldBe 1011
        }

        scenario("!can not be negative") {
            shouldThrow<IllegalArgumentException> {
                ShoppingCartAmount(10, -1)
            }
            shouldThrow<IllegalArgumentException> {
                ShoppingCartAmount(-1, 10)
            }
            shouldThrow<IllegalArgumentException> {
                ShoppingCartAmount(-1, -1)
            }
        }

        scenario("!Cent can not exceed 99 cents") {
            shouldThrow<IllegalArgumentException> {
                ShoppingCartAmount(10, 100)
            }
        }

        scenario("!A price can not exceed 300,00") {
            shouldThrow<TooHighPriceException> {
                Price(300, 1)
            }
        }

        scenario("!two amounts can not exceed 300,00 €") {
            shouldThrow<MaximumShoppingCardAmountExceededException> {
                ShoppingCartAmount(150, 0).plus(ShoppingCartAmount(150, 1))
            }
        }

        scenario("!an amount can be added to another") {
            ShoppingCartAmount(10,99).plus(ShoppingCartAmount(2,89)).valueInCent shouldBe 1388
            ShoppingCartAmount(0,99).plus(ShoppingCartAmount(0,99)).valueInCent shouldBe 198
            ShoppingCartAmount(111, 59).plus(ShoppingCartAmount(0 ,41)).valueInCent shouldBe 11200
        }

        scenario("!a zero amount added to another does not have any effect") {
            ShoppingCartAmount(10,99).plus(ShoppingCartAmount(0, 0)).valueInCent shouldBe 1099
        }


    }
})
