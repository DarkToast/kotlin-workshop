package example

import session3.PublicClass
import session3.VisibilityClass

object Usage {
    fun usage() {
        val visibilityClass = VisibilityClass(1, 2, 3)

        val internalClass: PublicClass = PublicClass()

        println(visibilityClass.publicValue)
    }
}
