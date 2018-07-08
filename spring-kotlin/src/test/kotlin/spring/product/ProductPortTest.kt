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

    val sku = "12345678"
    val productJson =
        """ |{
            |    "sku": "$sku",
            |    "name": "Bier",
            |    "ean": "12345678
            |}
        """.trimMargin()

    @Test
    fun whenCalled_shouldReturnHello() {
        val result = testRestTemplate.postForEntity("/admin/products", productJson, String::class.java)

        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)
        assertEquals(productJson, result?.body)
    }
}