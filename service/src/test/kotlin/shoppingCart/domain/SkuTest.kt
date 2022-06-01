package shoppingCart.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

/*
 * Aufgabe 2:
 * Entfernen Sie bitte die Kommentare von den hier drunter liegenden Tests und implementieren Sie in der Datei
 * `shoppingCart/domain/Product.kt` die Klasse SKU, die Dort der Klasse `Product` fehlt.
 *
 * Das Ziel ist, eine Kotlinklasse mit Vor- und Nachbedingungen zu implementieren.
*/
@Disabled
class SkuTest() {
    @Test
    fun `can contain up to 20 alphanumeric characters`() {
        assertDoesNotThrow {
            SKU("12345678901234567890")
        }
    }

    @Test
    fun `A SKU can contain 1 character`() {
        assertDoesNotThrow {
            SKU("1")
        }
    }

    @Test
    fun `are equals`() {
        assertTrue(SKU("1234567") == SKU("1234567"))
    }

    @Test
    fun `must not be empty`() {
        assertThrows<IllegalArgumentException> {
            SKU("")
        }
    }

    @Test
    fun `must not exceed 20 characters`() {
        assertThrows<IllegalArgumentException> {
            SKU("123456789013245678901")
        }
    }

    @Test
    fun `must contain alphanumeric characters`() {
        assertThrows<IllegalArgumentException> {
            SKU("13246<7890")
        }
    }


    @Test
    fun `A SKU can be represented as string`() {
        assertEquals("12345678901234567890", SKU("12345678901234567890").toString())
    }
}
