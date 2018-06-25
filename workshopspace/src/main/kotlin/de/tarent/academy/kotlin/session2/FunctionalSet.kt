package de.tarent.academy.kotlin.session2

typealias Set = (Int) -> Boolean
typealias Filter = (Int) -> Boolean

object FunctionSet {
    val emptySet: () -> Set = {
        { i: Int -> false }
    }

    val singleSet: (Int) -> Set = { element: Int ->
        { i: Int -> element == i }
    }

    val contains: (Set, Int) -> Boolean = { i, j -> TODO() }

    val union: (Set, Set) -> Set = { i , j -> TODO() }

    val intersect: (Set, Set) -> Set = { i , j -> TODO() }

    val diff: (Set, Set) -> Set = { i , j -> TODO() }

    val filterBound = 1000
    val filter: (Set, Filter) -> Set = { i , j -> TODO() }

    // Implement equals to filter
    val exists = null

    // Implement equals to filter
    val forall = null
}

