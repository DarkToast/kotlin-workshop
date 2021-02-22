package functional

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

typealias Set = (Int) -> Boolean


fun empty(): Set = { _ -> false }

fun contains(s: Set, i: Int): Boolean = s(i)

fun new(i: Int): Set = TODO()

fun union(a: Set, b: Set): Set = TODO()

fun intersect(a: Set, b: Set): Set = TODO()

fun diff(a: Set, b: Set): Set = TODO()

fun filter(a: Set, f: (Int) -> Boolean): Set = TODO()

@Disabled
class SetSpec {
    val a = new(1)
    val b = new(2)
    val c = new(5)

    @Test
    fun `an empty set has no a element`() {
        val set = empty()
        assertEquals(false, set(0))
    }

    @Test
    fun `a single set has the a element`() {
        val set = new(14)
        assertEquals(true, set(14))
        assertEquals(false, set(15))
    }

    @Test
    fun `contains checks for an element existence`() {
        val set = new(14)
        assertEquals(true, contains(set, 14))
        assertEquals(false, contains(set, 15))
    }

    @Test
    fun `union constructs a union set`() {
        val set = union(a, b)
        assertEquals(true, contains(set, 1))
        assertEquals(true, contains(set, 2))
        assertEquals(false, contains(set, 5))
    }

    @Test
    fun `a union of several sets has all elements`() {
        val set = union(a, union(b, c))
        assertEquals(true, contains(set, 1))
        assertEquals(true, contains(set, 2))
        assertEquals(true, contains(set, 5))
        assertEquals(false, contains(set, 1000))
    }

    @Test
    fun `an intersect contains all elements which are in both sets`() {
        val setAB = union(a, b)
        val setCB = union(c, b)

        val set = intersect(setAB, setCB)

        assertEquals(false, contains(set, 1))
        assertEquals(true, contains(set, 2))
        assertEquals(false, contains(set, 5))
    }

    @Test
    fun `an intersect contains no elements if both sets have no some equal elements`() {
        val setAB = union(a, b)
        val set = intersect(setAB, c)

        assertEquals(false, contains(set, 1))
        assertEquals(false, contains(set, 2))
        assertEquals(false, contains(set, 5))
    }

    @Test
    fun `a diff contains all elements which are NOT in both sets`() {
        val setAB = union(a, b)
        val setCB = union(c, b)

        val set = diff(setAB, setCB)

        assertEquals(true, contains(set, 1))
        assertEquals(false, contains(set, 2))
        assertEquals(true, contains(set, 5))
    }

    @Test
    fun `a diff contains all elements if both sets have no equal elements`() {
        val setAB = union(a, b)

        val set = diff(setAB, c)

        assertEquals(true, contains(set, 1))
        assertEquals(true, contains(set, 2))
        assertEquals(true, contains(set, 5))
    }

    @Test
    fun `filter returns a subset of the elements which matches the filter`() {
        val setABC = union(a, union(b, c))

        val set = filter(setABC) { i -> i >= 2 }

        assertEquals(false, contains(set, 1))
        assertEquals(true, contains(set, 2))
        assertEquals(true, contains(set, 5))
    }
}
