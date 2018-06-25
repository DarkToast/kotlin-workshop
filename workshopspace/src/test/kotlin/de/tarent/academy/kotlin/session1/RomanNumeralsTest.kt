package de.tarent.academy.kotlin.session1

import org.junit.Assert.*
import org.junit.Test

class RomanNumeralsTest {

    @Test
    fun `a roman numerals can match all single expressions`() {
        assertEquals(1, RomanNumerals().convert("I"));
        assertEquals(5, RomanNumerals().convert("V"));
        assertEquals(10, RomanNumerals().convert("X"));
        assertEquals(50, RomanNumerals().convert("L"));
        assertEquals(100, RomanNumerals().convert("C"));
        assertEquals(500, RomanNumerals().convert("D"));
        assertEquals(1000, RomanNumerals().convert("M"));
    }

    @Test
    fun `a roman numerals can match additional expressions`() {
        assertEquals(2, RomanNumerals().convert("II"));
        assertEquals(6, RomanNumerals().convert("VI"));
        assertEquals(20, RomanNumerals().convert("XX"));
        assertEquals(42, RomanNumerals().convert("XLII"));
    }

    @Test
    fun `a roman numerals can match substractional expressions`() {
        assertEquals(4, RomanNumerals().convert("IV"));
        assertEquals(9, RomanNumerals().convert("IX"));
        assertEquals(99, RomanNumerals().convert("XCIX"));
        assertEquals(2013, RomanNumerals().convert("MMXIII"));
    }
}