package de.tarent.academy.kotlin

import org.junit.Test
import org.junit.Assert.assertEquals

class JUnitCalculatorTest {
    @Test
    fun `sum can handle an empty list`() {
        // given: A calculator
        val calculator = Calculator()

        // when: an empty list
        val sum = calculator.sum(emptyList())

        // then: 0
        assertEquals("An empty must result in 0", 0, sum)
    }

    @Test
    fun `sum can handle list elements`() {
        // given: A calculator
        val calculator = Calculator()

        // when: a list of 1 to 10
        val sum = calculator.sum(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))

        // then: 55
        assertEquals("An empty must result in 55", 55, sum)
    }

    @Test
    fun `sum can handle negative elements`() {
        // given: A calculator
        val calculator = Calculator()

        // when: a list of -3 to 3
        val sum = calculator.sum(listOf(-3, -2, -1, 0, 1, 2, 3))

        // then: 0
        assertEquals("An empty must result in 0", 0, sum)
    }
}