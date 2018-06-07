package de.tarent.academy.kotlin

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.FeatureSpec

class KotlinTestCalculatorTest : FeatureSpec() {
    init {
        feature("sum") {
            val calculator = Calculator()

            scenario("empty list") {
                calculator.sum(emptyList()) shouldBe 0
            }

            scenario("elements from 1 to 10") {
                calculator.sum(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)) shouldBe 55
            }

            scenario("elements from -3 to 3") {
                calculator.sum(listOf(-3, -2, -1, 0, 1, 2, 3)) shouldBe 0
            }
        }
    }
}