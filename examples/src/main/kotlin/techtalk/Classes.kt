package techtalk

import javax.inject.Inject

open class MySimpleClass(private val value1: String, open val value2: String) {

    private val helloWorld: String = MyStaticObject.staticFun("Hallo ")

}

object MyStaticObject {
    fun staticFun(value1: String): String = "$value1 Welt"
}


class MySpringEntity @Inject constructor(
    private val value1: String, override val value2: String, val value3: String = "Hallo Welt "
) : MySimpleClass(value1, value2) {

    fun combine(): String = value1 + value2 + value3
}