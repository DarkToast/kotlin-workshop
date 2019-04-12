package shoppingCart.domain

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotThrow
import io.kotlintest.shouldThrow
import io.kotlintest.specs.FeatureSpec

class SkuTest: FeatureSpec({
    /*
     * Aufgabe 2:
     * Entfernen Sie bitte die Kommentare von den hier drunter liegenden Tests und implementieren Sie in der Datei
     * `shoppingCart/domain/Product.kt` die Klasse SKU, die Dort der Klasse `Product` fehlt.
     *
     * Das Ziel ist, eine Kotlinklasse mit Vor- und Nachbedingungen zu implementieren.
    */

    feature("A SKU") {
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


        scenario("A SKU can be represented as string") {
            SKU("12345678901234567890").toString() shouldBe "12345678901234567890"
        }
    }
})
