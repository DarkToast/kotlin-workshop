package com.qvest.digital.shoppingCart.domain

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows



/*
 * Eine fertige Implementierung mit Tests.
 * Folgende Anforderungen sind umgesetzt:
 * - Eine Quantity kann 0 aber nicht negativ sein.
 * - Eine Quantity kann maximal 10 sein. -> TooMuchItemsOfAProduct
 */
class QuantityTest {
    @Test
    fun `can be zero`() {
        assertDoesNotThrow { Quantity(0) }
    }

    @Test
    fun `can be up to 10`() {
        assertDoesNotThrow { Quantity(10) }
    }

    @Test
    fun `are equals`() {
        assertTrue(Quantity(10) == Quantity(10))
    }

    @Test
    fun `can not be negative`() {
        assertThrows<IllegalArgumentException> { Quantity(-1) }
    }

    @Test
    fun `can not exceed 10`() {
        assertThrows<TooMuchItemsOfAProduct> { Quantity(11) }
    }
}
