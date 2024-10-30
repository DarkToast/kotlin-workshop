package shoppingCart.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import shoppingCart.domain.money.Amount
import shoppingCart.domain.money.MaximumShoppingCardAmountExceededException

/*
 * Aufgabe 3:
 * Entfernen Sie bitte die @Disabled annotation von der hier drunter liegenden Klasse und implementieren Sie in der Datei
 * `shoppingCart/domain/Money.kt` die Klasse Amount und ihre Methoden.
 *
 * Das Ziel ist, eine Kotlinklasse mit Vor- und Nachbedingungen zu implementieren, welche auch ändernde Methoden
 * enthält. Folgende Anforderungen sollen umgesetzt sein:
 *
 * - Ein Amount kann 0 sein
 * - Ein Amount kann nicht negativ sein
 * - Ein Amount kann nicht größer als 300,00€ sein. -> MaximumShoppingCardAmountExceededException
 * - Ein Amount muss seinen Wert in Cents ausdrücken können
 * - Zwei Amounts können über `plus` addiert werden.
*/
class AmountTest() {
    @Test
    fun `can be zero`() {
        assertDoesNotThrow { Amount(0, 0) }
    }

    @Test
    fun `can be 300,00 €`() {
        assertDoesNotThrow { Amount(300, 0) }
    }

    @Test
    fun `are equals`() {
        assertTrue(Amount(300, 0) == Amount(300, 0))
    }

    @Test
    fun `can be 0,01 €`() {
        assertDoesNotThrow { Amount(0, 1) }
    }

    @Test
    fun `has a value in cent`() {
        assertEquals(1011, Amount(10, 11).valueInCent)
    }

    @Test
    fun `can not be negative`() {
        assertThrows<IllegalArgumentException> { Amount(10, -1) }
        assertThrows<IllegalArgumentException> { Amount(-1, 10) }
        assertThrows<IllegalArgumentException> { Amount(-1, -1) }
    }

    @Test
    fun `Cent can not exceed 99 cents`() {
        assertThrows<IllegalArgumentException> { Amount(10, 100) }
    }

    @Test
    fun `A amounts can not exceed 300,00`() {
        assertThrows<MaximumShoppingCardAmountExceededException> { Amount(300, 1) }
    }

    @Test
    fun `two amounts can not exceed 300,00 €`() {
        assertThrows<MaximumShoppingCardAmountExceededException> { Amount(150, 0).plus(Amount(150, 1)) }
    }

    @Test
    fun `an amount can be added to another`() {
        assertEquals(1388, Amount(10, 99).plus(Amount(2, 89)).valueInCent)
        assertEquals(198, Amount(0, 99).plus(Amount(0, 99)).valueInCent)
        assertEquals(11200, Amount(111, 59).plus(Amount(0, 41)).valueInCent)
    }

    @Test
    fun `a zero amount added to another does not have any effect`() {
        assertEquals(1099, Amount(10, 99).plus(Amount(0, 0)).valueInCent)
    }
}
