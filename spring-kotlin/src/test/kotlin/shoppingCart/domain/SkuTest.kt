package shoppingCart.domain

import de.tarent.ciwanzik.shoppingCart.domain.Quantity
import de.tarent.ciwanzik.shoppingCart.domain.SKU
import io.kotlintest.shouldNotThrow
import io.kotlintest.shouldThrow
import io.kotlintest.specs.FeatureSpec

class SkuTest: FeatureSpec({
    feature("A SKU") {
        scenario("must not be empty") {
            shouldThrow<IllegalArgumentException> {
                SKU("")
            }
        }

        scenario("must not exceed 20 characters") {
            shouldThrow<IllegalArgumentException> {
                SKU("123456789013245678901")
            }
        }

        scenario("must contain alphanumeric characters") {
            shouldThrow<IllegalArgumentException> {
                SKU("13246<7890")
            }
        }

        scenario("can contain up to 20 alphanumeric characters") {
            shouldNotThrow<IllegalArgumentException> {
                SKU("12345678901234567890")
            }
        }

        scenario("A SKU can contain 1 character") {
            shouldNotThrow<IllegalArgumentException> {
                SKU("1")
            }
        }
    }
})
