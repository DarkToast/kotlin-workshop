package workshop

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JUnitTest {
    @Test
    fun `when take view then register event bus`() {
        // given: A calculator
        val calculator = { x: Int, y: Int -> x + y }

        // when: 2 + 4
        val sum = calculator(2, 4)

        // then: 6
        assertEquals( 6, sum, "2 + 4 is 6")
    }
}
