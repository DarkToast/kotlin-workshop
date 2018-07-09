package spring.product

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import spring.App

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [App::class], webEnvironment = RANDOM_PORT)
class ProductPortTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun `we can create a new product`() {
        // given - a new product
        val articleNo = "12345678"
        val product = ProductView(articleNo, "Bier", articleNo)

        // when - post on the resource
        val result = testRestTemplate.postForEntity("/products", product, String::class.java)

        // then - http ok and the new product as response
        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)
        assertProductResponse(result.body ?: "", articleNo)
    }

    @Test
    fun `we can get an existing product`() {
        // given - an existing product
        val articleNo = "000001"
        anExistingProduct("000001")

        // when - a get on the articleNo is made
        val result = testRestTemplate.getForEntity("/products/$articleNo", String::class.java)

        // then - http ok and the product response
        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)
        assertProductResponse(result?.body ?: "", articleNo)
    }

    private fun assertProductResponse(responseBody: String, articleNo: String) {
        val productJson ="""{"articleNo":"$articleNo","name":"Bier","ean":"12345678"}""".trim()
        assertEquals(responseBody, productJson)
    }

    private fun anExistingProduct(articleNo: String) {
        val product = ProductView(articleNo, "Bier", "12345678")
        testRestTemplate.postForEntity("/products", product, String::class.java)
    }
}