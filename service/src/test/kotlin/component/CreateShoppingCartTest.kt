package component

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldMatch
import io.kotest.matchers.string.shouldNotContain
import io.kotest.spring.SpringListener
import shoppingCart.Application
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
class CreateShoppingCartTest() : FeatureSpec() {
    override fun listeners() = listOf(SpringListener)

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    init {
        feature("The shopping cart service") {
            scenario("a POST on /shoppingcart returns HTTP 201") {
                val response = restTemplate.postForEntity("/shoppingcart", "", String::class.java)

                response.statusCodeValue shouldBe 201
            }

            scenario("a POST on /shoppingcart returns a Location URI") {
                val response = restTemplate.postForEntity("/shoppingcart", "", String::class.java)

                response.headers.get("LOCATION")?.get(0)!! shouldMatch (Regex("""\/shoppingcart\/[\w\d-]+"""))
            }

            scenario("a created shoppingcart can be received by a GET") {
                val location = createShoppingCart()

                val response = restTemplate.getForEntity(location, String::class.java)

                response.statusCodeValue shouldBe 200
            }

            scenario("receiving a not existing shopping cart returns 404") {
                val location = "/shoppingcart/123e4567-e89b-12d3-a456-426655440000"

                val response = restTemplate.getForEntity(location, String::class.java)

                response.statusCodeValue shouldBe 404
            }

            scenario("malformed uuid return 400") {
                val location =  "/shoppingcart/not_a_uuid"

                val response = restTemplate.getForEntity(location, String::class.java)

                response.statusCodeValue shouldBe 400
            }

            scenario("a bad request does not exposure the internal runtime") {
                val location =  "/shoppingcart/not_a_uuid"

                val response = restTemplate.getForEntity(location, String::class.java)

                response.body shouldNotContain  "java.lang.String"
                response.body shouldNotContain  "java.util.UUID"
            }
        }
    }

    private fun createShoppingCart(): String {
        val response = restTemplate.postForEntity("/shoppingcart", "", String::class.java)
        return response.headers.get("LOCATION")?.get(0) ?: ""
    }

}
