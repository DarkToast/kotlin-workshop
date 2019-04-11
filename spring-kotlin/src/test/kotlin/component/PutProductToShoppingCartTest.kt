package component

import de.tarent.ciwanzik.Application
import de.tarent.ciwanzik.shoppingCart.ports.driver.rest.dto.GetProduct
import io.kotlintest.matchers.string.shouldMatch
import io.kotlintest.shouldBe
import io.kotlintest.specs.FeatureSpec
import io.kotlintest.spring.SpringListener
import org.json.JSONArray
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.json.JSONObject
import org.springframework.http.*
import org.springframework.util.MultiValueMap

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
class PutProductToShoppingCartTest : FeatureSpec() {
    override fun listeners() = listOf(SpringListener)

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    init {
        feature("Adding products to the shopping cart") {
            scenario("!an existing shopping cart has an addProduct link") {
                val response = createAndGetShoppingCart()

                val json = JSONObject(response.body)
                val link: JSONObject = json?.optJSONObject("links")?.optJSONObject("addProduct") ?: JSONObject("{}")

                link.optString("href") shouldMatch (Regex("""\/shoppingcart\/[\w\d-]+"""))
                link.optString("method") shouldBe "PUT"
            }

            scenario("!a new shopping cart has no products") {
                val response = createAndGetShoppingCart()

                val products = extractProducts(response)
                products.size shouldBe 0
            }

            scenario("!adding a new product returns status 200") {
                val location = createShoppingCart()
                val product = """{ "sku": "123456", "quantity": "2" }"""

                val response = addProduct(location, product)

                response.statusCodeValue shouldBe 200
            }

            scenario("adding a unknown product returns status 404") {
                val location = createShoppingCart()
                val product = """{ "sku": "4711unknown", "quantity": "2" }"""

                val response = addProduct(location, product)

                response.statusCodeValue shouldBe 404
            }

            scenario("adding a invalid SKU returns status 400") {
                val location = createShoppingCart()
                val product = """{ "sku": "-1", "quantity": "2" }"""

                val response = addProduct(location, product)

                response.statusCodeValue shouldBe 400
            }

            scenario("adding two much quantity return status 400") {
                val location = createShoppingCart()
                val product = """{ "sku": "123456", "quantity": "11" }"""

                val response = addProduct(location, product)

                response.statusCodeValue shouldBe 400
            }

            scenario("!an added product can be received") {
                val location = createShoppingCart()
                val newProduct = """{ "sku": "123456", "quantity": "2" }"""

                addProduct(location, newProduct)

                val response = getShoppingCart(location)

                val products = extractProducts(response)
                products.size shouldBe 1
                products[0].name shouldBe "Brot"
                products[0].sku shouldBe "123456"
            }

            scenario("!two products are added and received") {
                val location = createShoppingCart()
                val firstProduct = """{ "sku": "123456", "quantity": "2" }"""
                val secondProduct = """{ "sku": "654321", "quantity": "3" }"""

                addProduct(location, firstProduct)
                addProduct(location, secondProduct)

                val response = getShoppingCart(location)

                val products = extractProducts(response)
                products.size shouldBe 2
                products[0].name shouldBe "Brot"
                products[0].sku shouldBe "123456"
                products[1].name shouldBe "Milch"
                products[1].sku shouldBe "654321"
            }
        }
    }

    private fun extractProducts(getShoppingCartResponse: ResponseEntity<String>): List<GetProduct> {
        val json = JSONObject(getShoppingCartResponse.body)
        val items: JSONArray = json.optJSONArray("items")

        val productList: MutableList<GetProduct> = mutableListOf()

        for (i in 0 until items.length()) {
            val product = (items.get(i) as JSONObject).getJSONObject("product")
            productList += GetProduct(product.optString("sku"), product.optString("name"))
        }


        return productList.toList()
    }

    private fun createAndGetShoppingCart(): ResponseEntity<String> {
        return getShoppingCart(createShoppingCart())
    }

    private fun getShoppingCart(location: String): ResponseEntity<String> {
        return restTemplate.getForEntity(location, String::class.java)
    }

    private fun createShoppingCart(): String {
        val response = restTemplate.postForEntity("/shoppingcart", "", String::class.java)
        return response.headers["LOCATION"]?.get(0) ?: ""
    }

    private fun addProduct(location: String, product: String): ResponseEntity<String> {
        val header = HttpHeaders()
        header.contentType = MediaType.APPLICATION_JSON_UTF8

        val entity = HttpEntity(product, header)
        return restTemplate.exchange(location, HttpMethod.PUT, entity, String::class.java, "")
    }

}
