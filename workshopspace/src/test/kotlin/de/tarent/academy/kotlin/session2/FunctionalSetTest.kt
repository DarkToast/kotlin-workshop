package de.tarent.academy.kotlin.session2

import de.tarent.academy.kotlin.session2.FunctionSet.contains
import de.tarent.academy.kotlin.session2.FunctionSet.diff
import de.tarent.academy.kotlin.session2.FunctionSet.emptySet
import de.tarent.academy.kotlin.session2.FunctionSet.exists
import de.tarent.academy.kotlin.session2.FunctionSet.filter
import de.tarent.academy.kotlin.session2.FunctionSet.forall
import de.tarent.academy.kotlin.session2.FunctionSet.intersect
import de.tarent.academy.kotlin.session2.FunctionSet.singleSet
import de.tarent.academy.kotlin.session2.FunctionSet.union
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertEquals


class FunctionalSetTest {

    @Test
    fun `an empty set has not elements`() {
        val set = emptySet()

        assertEquals(false, set(1))
        assertEquals(false, set(2))
    }

    @Test
    fun `a single set has an element`() {
        val set = singleSet(10)

        assertEquals(false, set(5))
        assertEquals(true, set(10))
    }

    @Test
    fun `contains checks a set`() {
        val set = singleSet(10)

        assertEquals(false, contains(set, 5))
        assertEquals(true, contains(set, 10))
    }

    @Test
    fun `union constructs a union set`() {
        val setOne = singleSet(10)
        val setTwo = singleSet(15)

        val unionSet = union(setOne, setTwo)

        assertEquals(false, unionSet(5))
        assertEquals(true, unionSet(10))
        assertEquals(true, unionSet(15))
    }

    @Test
    fun `a union of several sets has all elements`() {
        val unionSet = union(singleSet(10), union(singleSet(15), singleSet(20)))

        assertEquals(false, unionSet(5))
        assertEquals(false, unionSet(10))
        assertEquals(true, unionSet(15))
        assertEquals(true, unionSet(20))
    }

    @Test
    fun `an intersect contains all elements which are in both sets`() {
        val setOne = union(singleSet(1), singleSet(2))
        val setTwo = union(singleSet(2), singleSet(3))

        val intersectSet = intersect(setOne, setTwo)

        assertEquals(false, intersectSet(1))
        assertEquals(true, intersectSet(2))
        assertEquals(false, intersectSet(3))
    }

    @Test
    fun `an intersect contains no elements if both sets have no some equal elements`() {
        val setOne = union(singleSet(1), singleSet(2))
        val setTwo = union(singleSet(3), singleSet(4))

        val intersectSet = intersect(setOne, setTwo)

        assertEquals(false, intersectSet(1))
        assertEquals(false, intersectSet(2))
        assertEquals(false, intersectSet(3))
        assertEquals(false, intersectSet(4))
    }

    @Test
    fun `a diff contains all elements which are NOT in both sets`() {
        val setOne = union(singleSet(1), singleSet(2))
        val setTwo = union(singleSet(2), singleSet(3))

        val diffSet = diff(setOne, setTwo)

        assertEquals(true, diffSet(1))
        assertEquals(false, diffSet(2))
        assertEquals(true, diffSet(3))
    }

    @Test
    fun `a diff contains all elements if both sets have no equal elements`() {
        val setOne = union(singleSet(1), singleSet(2))
        val setTwo = union(singleSet(3), singleSet(4))

        val diffSet = diff(setOne, setTwo)

        assertEquals(true, diffSet(1))
        assertEquals(true, diffSet(2))
        assertEquals(true, diffSet(3))
        assertEquals(true, diffSet(4))
    }

    @Test
    fun `filter returns a subset of the elements which matches the filter`() {
        val set = union(singleSet(1), union(singleSet(2), singleSet(3)))

        val filterSet = filter(set, { i -> i >= 2 })

        assertEquals(true, filterSet(1))
        assertEquals(false, filterSet(2))
        assertEquals(false, filterSet(3))
    }

    @Test
    fun `filter can take every filter function`() {
        val set = union(singleSet(1), union(singleSet(2), singleSet(3)))

        val filterSet = filter(set, { i -> false })

        assertEquals(false, filterSet(1))
        assertEquals(false, filterSet(2))
        assertEquals(false, filterSet(3))
    }

    @Test
    fun `exists checks for the existential quantification`() {
        val set = union(singleSet(1), union(singleSet(2), singleSet(3)))

//        assertTrue(exists(set, { i -> i % 2 == 0 }))
//        assertFalse(exists(set, { i -> i > 4 }))

        TODO()
    }

    @Test
    fun `forall checks for the universal quantification`() {
        val set = union(singleSet(1), union(singleSet(2), singleSet(3)))

//        assertTrue(forall(set, { i -> i > 0 }))
//        assertFalse(forall(set, { i -> i % 2 == 0 }))

        TODO()
    }

}