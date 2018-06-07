package de.tarent.academy.kotlin

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class SpekCalculatorTest: Spek({

    describe("sum") {
        val calculator = Calculator()

        on("an empty list") {
            val sum = calculator.sum(emptyList())

            it("should be 0") {
                assertEquals(0, sum)
            }
        }

        on("elements from 1 to 10") {
            val sum = calculator.sum(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))

            it("should be 55") {
                assertEquals(55, sum)
            }
        }

        on("elements from -3 to 3") {
            val sum = calculator.sum(listOf(-3, -2, -1, 0, 1, 2, 3))

            it("should be 0") {
                assertEquals(0, sum)
            }
        }
    }
})