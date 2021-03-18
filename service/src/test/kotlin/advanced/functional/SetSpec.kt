package advanced.functional

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe

typealias Set = (Int) -> Boolean


fun empty(): Set = { _ -> false }

fun contains(s: Set, i: Int): Boolean = s(i)

fun new(i: Int): Set = TODO()

fun union(a: Set, b: Set): Set = TODO()

fun intersect(a: Set, b: Set): Set = TODO()

fun diff(a: Set, b: Set): Set = TODO()

fun filter(a: Set, f: (Int) -> Boolean): Set = TODO()


class SetSpec : FeatureSpec({
    feature("!A Set") {
        val a = new(1)
        val b = new(2)
        val c = new(5)

        scenario("an empty set has no a element") {
            val set = empty()
            set(0) shouldBe false
        }

        scenario("a single set has the a element") {
            val set = new(14)
            set(14) shouldBe true
            set(15) shouldBe false
        }

        scenario("contains checks for an element existence") {
            val set = new(14)
            contains(set, 14) shouldBe true
            contains(set, 15) shouldBe false
        }

        scenario("union constructs a union set") {
            val set = union(a, b)
            contains(set, 1) shouldBe true
            contains(set, 2) shouldBe true
            contains(set, 5) shouldBe false
        }

        scenario("a union of several sets has all elements") {
            val set = union(a, union(b, c))
            contains(set, 1) shouldBe true
            contains(set, 2) shouldBe true
            contains(set, 5) shouldBe true
            contains(set, 1000) shouldBe false
        }

        scenario("an intersect contains all elements which are in both sets") {
            val setAB = union(a, b)
            val setCB = union(c, b)

            val set = intersect(setAB, setCB)

            contains(set, 1) shouldBe false
            contains(set, 2) shouldBe true
            contains(set, 5) shouldBe false
        }

        scenario("an intersect contains no elements if both sets have no some equal elements") {
            val setAB = union(a, b)
            val set = intersect(setAB, c)

            contains(set, 1) shouldBe false
            contains(set, 2) shouldBe false
            contains(set, 5) shouldBe false
        }

        scenario("a diff contains all elements which are NOT in both sets") {
            val setAB = union(a, b)
            val setCB = union(c, b)

            val set = diff(setAB, setCB)

            contains(set, 1) shouldBe true
            contains(set, 2) shouldBe false
            contains(set, 5) shouldBe true
        }

        scenario("a diff contains all elements if both sets have no equal elements") {
            val setAB = union(a, b)

            val set = diff(setAB, c)

            contains(set, 1) shouldBe true
            contains(set, 2) shouldBe true
            contains(set, 5) shouldBe true
        }

        scenario("filter returns a subset of the elements which matches the filter") {
            val setABC = union(a, union(b, c))

            val set = filter(setABC) { i -> i >= 2 }

            contains(set, 1) shouldBe false
            contains(set, 2) shouldBe true
            contains(set, 5) shouldBe true
        }
    }
})