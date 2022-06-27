package shoppingCart.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class PriceTest() {


/*
 * Aufgabe 4:
 * Entfernen Sie bitte die @Disabled annotation von der hier drunter liegenden Klasse und implementieren Sie in der Datei
 * `shoppingCart/domain/Money.kt` die Klasse Price und ihre Methoden.
 *
 * Das Ziel ist, eine Kotlinklasse mit Vor- und Nachbedingungen zu implementieren, welche auch ändernde Methoden
 * enthält. Folgende Anforderungen sollen umgesetzt sein:
 *
 * - Ein Price kann 0 sein.
 * - Ein Price kann nicht negativ sein.
 * - Ein Price kann nicht größer als 120,00€ sein. --> TooHighPriceException
 * - Ein Price muss seinen Wert in Cents ausdrücken können.
 * - Zwei Price können über `plus` addiert werden.
 * - Ein Price kann mit `times` multipliziert werden.
 * - Ein addierter oder multiplizierter Price muss den oberen Invarianten entsprechen.
*/

    @Test
    fun `can be zero`() {
        assertDoesNotThrow { Price(0, 0) }
    }

    @Test
    fun `can be 120,00 €`() {
        assertDoesNotThrow { Price(120, 0) }
    }

    @Test
    fun `are equals`() {
        assertTrue(Price(120, 0) == Price(120, 0))
    }

    @Test
    fun `can be 0,01 €`() {
        assertDoesNotThrow { Price(0, 1) }
    }

    @Test
    fun `can be created with cents`() {
        assertEquals(Price(10, 15), Price(Price(10, 15).valueInCent))
    }

    @Test
    fun `has a value in cent`() {
        assertEquals(1011, Price(10, 11).valueInCent)
    }

    @Test
    fun `can not be negative`() {
        assertThrows<IllegalArgumentException> { Price(10, -1) }
        assertThrows<IllegalArgumentException> { Price(-1, 10) }
        assertThrows<IllegalArgumentException> { Price(-1, -1) }
    }

    @Test
    fun `Cent can not exceed 99 cents`() {
        assertThrows<IllegalArgumentException> {
            Price(10, 100)
        }
    }

    @Test
    fun `A price can not exceed 120,00`() {
        assertThrows<TooHighPriceException> {
            Price(120, 1)
        }
    }

    @Test
    fun `a price can be added to another`() {
        assertEquals(1388, Price(10, 99).plus(Price(2, 89)).valueInCent)
        assertEquals(198, Price(0, 99).plus(Price(0, 99)).valueInCent)
        assertEquals(11200, Price(111, 59).plus(Price(0, 41)).valueInCent)
    }

    @Test
    fun `a zero price added to another does not have any effect`() {
        assertEquals(1099, Price(10, 99).plus(Price(0, 0)).valueInCent)
    }

    @Test
    fun `two combined prices can not exceed 120,00 €`() {
        assertThrows<TooHighPriceException> {
            Price(50, 99).plus(Price(69, 2))
        }
    }

    @Test
    fun `can be multiply with a quantity`() {
        assertEquals(2000, Price(10, 0).times(Quantity(2)).valueInCent)
    }

    @Test
    fun `can be multiply with a quantity 0`() {
        assertEquals(0, Price(10, 0).times(Quantity(0)).valueInCent)
    }

    @Test
    fun `can be multiply with a quantity 1`() {
        assertEquals(1000, Price(10, 0).times(Quantity(1)).valueInCent)
    }
}
