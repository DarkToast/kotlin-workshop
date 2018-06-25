package workshop

import org.junit.Test
import org.junit.Assert.assertEquals

class JUnitTest {
    @Test
    fun `when take view then register event bus`() {
        // given: A calculator
        val calculator = { x: Int, y: Int -> x + y }

        // when: 2 + 4
        val sum = calculator(2, 4)

        // then: 6
        assertEquals("2 + 4 is not 6", 6, sum)
    }
}