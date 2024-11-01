package shoppingCart.ports.rest

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import shoppingCart.application.ShoppingCartService
import shoppingCart.domain.Quantity
import shoppingCart.domain.SKU
import shoppingCart.domain.ShoppingCartUuid
import shoppingCart.ports.PortException
import shoppingCart.ports.rest.dto.PutProduct
import shoppingCart.ports.rest.dto.ShoppingCartDto
import java.net.URI
import java.util.UUID

class ShoppingCartNotFoundException(uuid: ShoppingCartUuid) :
    PortException("The shopping cart with id $uuid is unknown.")

/**
 * Generell braucht in Spring nicht jede Controllermethode Exceptions behandeln.
 * Hierfür existieren explizite ExceptionHandler, welche Exception pro Typ behandeln, egal wo
 * so geworfen wurden.
 *
 * @see LastLineOfDefenseErrorHandler
 */
@Controller
class ShoppingCartController(private val shoppingCartService: ShoppingCartService) {

    private val logger = KotlinLogging.logger {}

    @RequestMapping(path = ["/shoppingcart/{uuid}"], method = [RequestMethod.GET])
    fun getShoppingCart(@PathVariable uuid: UUID): ResponseEntity<ShoppingCartDto> {
        logger.info { "GET shopping cart." }

        val shoppingCartUuid = ShoppingCartUuid(uuid)
        val shoppingCart = shoppingCartService.showShoppingCart(shoppingCartUuid)

        return if(shoppingCart != null) {
            ResponseEntity.ok(ShoppingCartDto.fromDomain(shoppingCart))
        } else {
            logger.warn { "Shopping cart was not found." }
            ResponseEntity.notFound().build()
        }
    }

    @RequestMapping(path = ["/shoppingcart"], method = [RequestMethod.POST])
    fun postNewShoppingCart(): ResponseEntity<ShoppingCartDto> {
        logger.info { "POST - Create new shopping cart" }

        val cart = shoppingCartService.takeNewShoppingCart()
        val uri = URI("/shoppingcart/${cart.shoppingCartUuid}")

        return ResponseEntity.created(uri).build()
    }

    /*
     * RequestBody:
     *  In Spring kann der RequestBody und auch Pfadvariablen direkt deserialisiert werden.
     *  Da Kotlin-Data classes keinen Defaultkonstruktor und non-nullable types haben, haben es Liraries die Jackson
     *  schwer, Kotlinklassen wie erdacht zu benutzen. Stattdessen hat dieser Service für den REST-Layer eigene Data
     *  Transfer Objekte (DTOs)
     *
     *  Ein ProduktDto besteht in Form der Klasse PutProduct. Ein Zugriff erhält man mit einem mit @RequestBody annotierten
     *  Parameter seiner Methode: `@RequestBody putProductDto: PutProduct`
     *
     * ResponseEntity:
     *   In Spring werden HTTP Responses über die Klasse `ResponseEntity` zurückgegeben. Sie besitzt bereits Convenient-
     *   methoden wie `created`, `ok` oder `notFound`. Als Response Body können hier beliebige Objekte angegeben werden.
     *   Beispiel in Zeile 37. Der Jackson Objektmapper kümmert sich dann um die Serialisierung nach JSON.
     *   `ResponseEntity` kann aber auch normal instantiiert werden.
     */
    @RequestMapping(path = ["/shoppingcart/{uuid}/items"], method = [RequestMethod.PUT])
    fun putProductToShoppingCart(
        @PathVariable uuid: UUID,
        @RequestBody putProductDto: PutProduct
    ): ResponseEntity<ShoppingCartDto> {
        logger.info { "PUT - Adding product to shopping cart" }

        val shoppingCartUuid = ShoppingCartUuid(uuid)
        return if (putProductDto.sku != null && putProductDto.quantity != null) {
            val sku = SKU(putProductDto.sku)
            val quantity = Quantity(putProductDto.quantity)

            val shoppingCart = shoppingCartService.putProductIntoShoppingCart(shoppingCartUuid, sku, quantity)

            return if(shoppingCart != null) {
                ResponseEntity.ok(ShoppingCartDto.fromDomain(shoppingCart))
            } else {
                logger.warn { "Shopping cart was not found." }
                ResponseEntity.notFound().build()
            }
        } else {
            ResponseEntity.badRequest().build()
        }
    }
}