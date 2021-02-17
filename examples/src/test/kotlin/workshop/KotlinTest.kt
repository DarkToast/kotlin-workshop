package workshop

import io.kotlintest.shouldBe
import io.kotlintest.specs.FeatureSpec

class MyTests : FeatureSpec() {
    init {
        feature("the thingy bob") {
            val calculator = { x: Int, y: Int -> x + y }

            scenario("2 + 4 = 6") {
                calculator(2, 4).shouldBe(6)
            }
            scenario("-2 + 4 = 2") {
                calculator(-2, 4).shouldBe(2)
            }
        }
    }
}
