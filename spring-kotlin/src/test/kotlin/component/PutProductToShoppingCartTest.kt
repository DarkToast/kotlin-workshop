package component

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldMatch
import io.kotest.spring.SpringListener
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import shoppingCart.Application
import shoppingCart.ports.driver.rest.dto.GetProduct

/*
 * Aufgabe: 7
 *
 * Dieser Test testet die gesamte Komponente für das Feature `put product`.
 * Nachdem Sie die Aufgaben 1 - 6 abgeschlossen haben, fehlt nun noch die REST-Schnittstelle.
 *
 * Bitte implementieren Sie einen REST Endpunkt, der ihren in Aufgabe 6 geschriebenen Service aufruft.
 *
 * Einige Teste haben eine Beschreibung, die mit einem '!' beginnen. Diese sind zur Zeit inaktiv. Entfernen Sie
 * bitte das Ausrufezeichen, um den Test zu aktivieren und ihn implementieren zu können.
 *
 * Die Tests besitzen das Level 1 und Level 2. Fangen Sie bitte zunächst mit Level 1 an.
 *
 * Das Ziel ist, die Interoperabilität zwischen Javalibraries und Kotlin und seinen Möglichkeiten kennen zu lernen.
*/

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Application::class])
class PutProductToShoppingCartTest : FeatureSpec() {
    override fun listeners() = listOf(SpringListener)

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    init {
        feature("Adding products to the shopping cart") {
            scenario("an existing shopping cart has an addProduct link") {
                val response = createAndGetShoppingCart()

                val json = JSONObject(response.body)
                val link: JSONObject = json.optJSONObject("links")?.optJSONObject("addProduct") ?: JSONObject("{}")

                link.optString("href") shouldMatch (Regex("""\/shoppingcart\/[\w\d-]+\/items"""))
                link.optString("method") shouldBe "PUT"
            }

            scenario("a new shopping cart has no products") {
                val response = createAndGetShoppingCart()

                val products = extractProducts(response)
                products.size shouldBe 0
            }

            scenario("! level 1 - adding a new product returns status 200") {
                val location = createShoppingCart()
                val product = """{ "sku": "123456", "quantity": "2" }"""

                val response = addProduct(location, product)

                response.statusCodeValue shouldBe 200
            }

            scenario("! level 1 - an added product can be received") {
                val location = createShoppingCart()
                val newProduct = """{ "sku": "123456", "quantity": "2" }"""

                addProduct(location, newProduct)

                val response = getShoppingCart(location)

                val products = extractProducts(response)
                products.size shouldBe 1
                products[0].name shouldBe "Brot"
                products[0].sku shouldBe "123456"
            }

            scenario("! level 1 - two products are added and received") {
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

            scenario("! level 2 - adding a invalid SKU returns status 400") {
                val location = createShoppingCart()
                val product = """{ "sku": "-1", "quantity": "2" }"""

                val response = addProduct(location, product)

                response.statusCodeValue shouldBe 400
            }

            scenario("! level 2 - adding two much quantity return status 400") {
                val location = createShoppingCart()
                val product = """{ "sku": "123456", "quantity": "11" }"""

                val response = addProduct(location, product)

                response.statusCodeValue shouldBe 400
            }

            scenario("! level 2 - adding a unknown product returns status 404") {
                val location = createShoppingCart()
                val product = """{ "sku": "4711unknown", "quantity": "2" }"""

                val response = addProduct(location, product)

                response.statusCodeValue shouldBe 404
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
        val response = getShoppingCart(location)
        val json = JSONObject(response.body)
        val putProductLocation: String = json.optJSONObject("links")?.optJSONObject("addProduct")?.optString("href")
            ?: ""

        val header = HttpHeaders()
        header.contentType = MediaType.APPLICATION_JSON

        val entity = HttpEntity(product, header)
        return restTemplate.exchange(putProductLocation, HttpMethod.PUT, entity, String::class.java, "")
    }
}
