package component

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import com.qvest.digital.shoppingCart.Application

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
class CreateShoppingCartTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun `a POST on shoppingcart returns HTTP 201`() {
        val response = restTemplate.postForEntity("/shoppingcart", "", String::class.java)

        assertEquals(201, response.statusCode.value())
    }

    @Test
    fun `a POST on shoppingcart returns a Location URI`() {
        val response = restTemplate.postForEntity("/shoppingcart", "", String::class.java)

        val location = response.headers["LOCATION"]?.get(0)!!
        assertNotNull(location)
        assertTrue(location.contains("""\/shoppingcart\/[\w\d-]+""".toRegex()))
    }

    @Test
    fun `a created shoppingcart can be received by a GET`() {
        val location = createShoppingCart()

        val response = restTemplate.getForEntity(location, String::class.java)

        assertEquals(200, response.statusCode.value())
    }

    @Test
    fun `receiving a not existing shopping cart returns 404`() {
        val location = "/shoppingcart/123e4567-e89b-12d3-a456-426655440000"

        val response = restTemplate.getForEntity(location, String::class.java)

        assertEquals(404, response.statusCode.value())
    }

    @Test
    fun `malformed uuid return 400`() {
        val location = "/shoppingcart/not_a_uuid"

        val response = restTemplate.getForEntity(location, String::class.java)

        assertEquals(400, response.statusCode.value())
    }


    @Test
    fun `a bad request does not exposure the internal runtime`() {
        val location = "/shoppingcart/not_a_uuid"

        val response = restTemplate.getForEntity(location, String::class.java)
        val body = response.body!!
        assertNotNull(body)
        assertFalse(body.contains("java.lang.String"))
        assertFalse(body.contains("java.util.UUID"))
    }

    private fun createShoppingCart(): String {
        val response = restTemplate.postForEntity("/shoppingcart", "", String::class.java)
        return response.headers["LOCATION"]?.get(0) ?: ""
    }
}

