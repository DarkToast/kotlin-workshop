package shoppingCart.domain

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import shoppingCart.domain.Quantity
import shoppingCart.domain.TooMuchItemsOfAProduct


class QuantityTest: FeatureSpec({
    feature("A quantity") {
        scenario("can be zero") {
            shouldNotThrow<Throwable> {
                Quantity(0)
            }
        }

        scenario("can be up to 10") {
            shouldNotThrow<Throwable> {
                Quantity(10)
            }
        }

        scenario("are equals") {
            (Quantity(10) == Quantity(10)) shouldBe true
        }

        scenario("can not be negative") {
            shouldThrow<IllegalArgumentException> {
                Quantity(-1)
            }
        }

        scenario("can not exceed 10") {
            shouldThrow<TooMuchItemsOfAProduct> {
                Quantity(11)
            }
        }
    }
})
