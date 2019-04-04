package shoppingCart.domain

import de.tarent.ciwanzik.shoppingCart.domain.Quantity
import de.tarent.ciwanzik.shoppingCart.domain.TooMuchItemsOfAProduct
import io.kotlintest.shouldNotThrow
import io.kotlintest.shouldThrow
import io.kotlintest.specs.FeatureSpec


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
