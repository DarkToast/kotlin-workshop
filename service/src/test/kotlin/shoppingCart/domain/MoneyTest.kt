package shoppingCart.domain

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe

class MoneyTest: FeatureSpec({
    /*
     * Aufgabe 3:
     * Entfernen Sie bitte die Kommentare von den hier drunter liegenden Tests und implementieren Sie in der Datei
     * `shoppingCart/domain/Money.kt` die Klasse ShoppingCartAmount und ihre Methoden.
     *
     * Einige Teste haben eine Beschreibung, die mit einem '!' beginnen. Diese sind zur Zeit inaktiv. Entfernen Sie
     * bitte das Ausrufezeichen, um den Test zu aktivieren und ihn implementieren zu können.
     *
     * Das Ziel ist, eine Kotlinklasse mit Vor- und Nachbedingungen zu implementieren, welche auch ändernde Methoden
     * enthält.
    */
    feature("An amount") {
        scenario("can be zero") {
            shouldNotThrow<Throwable> {
                Amount(0, 0)
            }
        }

        scenario("can be 300,00 €") {
            shouldNotThrow<Throwable> {
                Amount(300, 0)
            }
        }

        scenario("are equals") {
            (Amount(300, 0) == Amount(300, 0)) shouldBe true
        }

        scenario("can be 0,01 €") {
            shouldNotThrow<Throwable> {
                Amount(0, 1)
            }
        }

        scenario("has a value in cent") {
            Amount(10, 11).valueInCent shouldBe 1011
        }

        scenario("can not be negative") {
            shouldThrow<IllegalArgumentException> {
                Amount(10, -1)
            }
            shouldThrow<IllegalArgumentException> {
                Amount(-1, 10)
            }
            shouldThrow<IllegalArgumentException> {
                Amount(-1, -1)
            }
        }

        scenario("Cent can not exceed 99 cents") {
            shouldThrow<IllegalArgumentException> {
                Amount(10, 100)
            }
        }

        scenario("A amounts can not exceed 300,00") {
            shouldThrow<MaximumShoppingCardAmountExceededException> {
                Amount(300, 1)
            }
        }

        scenario("two amounts can not exceed 300,00 €") {
            shouldThrow<MaximumShoppingCardAmountExceededException> {
                Amount(150, 0).plus(Amount(150, 1))
            }
        }

        scenario("an amount can be added to another") {
            Amount(10,99).plus(Amount(2,89)).valueInCent shouldBe 1388
            Amount(0,99).plus(Amount(0,99)).valueInCent shouldBe 198
            Amount(111, 59).plus(Amount(0 ,41)).valueInCent shouldBe 11200
        }

        scenario("a zero amount added to another does not have any effect") {
            Amount(10,99).plus(Amount(0, 0)).valueInCent shouldBe 1099
        }
    }

    /*
     * Aufgabe 4:
     * Entfernen Sie bitte die Kommentare von den hier drunter liegenden Tests und implementieren Sie in der Datei
     * `shoppingCart/domain/Money.kt` die Klasse Price und ihre Methoden
     *
     * Das Ziel ist, eine Kotlinklasse mit Vor- und Nachbedingungen zu implementieren, welche auch ändernde Methoden
     * enthält.
    */
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

        scenario("are equals") {
            (Price(120, 0) == Price(120, 0)) shouldBe true
        }

        scenario("can be 0,01 €") {
            shouldNotThrow<Throwable> {
                Price(0, 1)
            }
        }

        scenario("can be created with cents") {
            shouldNotThrow<Throwable> {
                Price(Price(10, 15).valueInCent) shouldBe Price(10,15)
            }
        }

        scenario("has a value in cent") {
            Price(10, 11).valueInCent shouldBe 1011
        }

        scenario("can not be negative") {
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

        scenario("Cent can not exceed 99 cents") {
            shouldThrow<IllegalArgumentException> {
                Price(10, 100)
            }
        }

        scenario("A price can not exceed 120,00") {
            shouldThrow<TooHighPriceException> {
                Price(120, 1)
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

        scenario("two combined prices can not exceed 120,00 €") {
            shouldThrow<TooHighPriceException> {
                Price(50, 99).plus(Price(69, 2))
            }
        }

        scenario("can be multiply with a quantity") {
            Price(10, 0).times(Quantity(2)).valueInCent shouldBe 2000
        }

        scenario("can be multiply with a quantity 0") {
            Price(10, 0).times(Quantity(0)).valueInCent shouldBe 0
        }

        scenario("can be multiply with a quantity 1") {
            Price(10, 0).times(Quantity(1)).valueInCent shouldBe 1000
        }
    }
})
