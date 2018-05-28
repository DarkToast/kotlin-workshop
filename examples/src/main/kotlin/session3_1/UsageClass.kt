package session3_1

import session3.VisibilityClass

object Usage {
    val visibilityClass: VisibilityClass = VisibilityClass(0, 1, 2)

    fun bar() {
        println(visibilityClass.internalValue)
        println(visibilityClass.publicValue)
    }
}