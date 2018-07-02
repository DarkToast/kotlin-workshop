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

    val contains: (Set, Int) -> Boolean = { set, element -> set(element) }

    val union: (Set, Set) -> Set = { set1: Set, set2: Set ->
        { element: Int -> set1(element) || set2(element) }
    }

    val intersect: (Set, Set) -> Set = { set1: Set, set2: Set ->
        { element: Int -> set1(element) && set2(element) }
    }
    val diff: (Set, Set) -> Set = { set1: Set, set2: Set ->
        { element: Int ->
            (set1(element) && !set2(element)) ||
            (!set1(element) && set2(element))
        }
    }
    val filterBound = 1000
    val filter: (Set, Filter) -> Set = { i , j -> TODO() }

    // Implement equals to filter
    val exists = null

    // Implement equals to filter
    val forall = null
}

