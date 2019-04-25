package shoppingCart.ports.driver.rest

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import shoppingCart.application.ShoppingCartService
import shoppingCart.domain.ShoppingCartUuid
import shoppingCart.ports.PortException
import shoppingCart.ports.driver.rest.dto.ShoppingCartDto
import java.net.URI
import java.util.UUID

class ShoppingCartNotFoundException(uuid: ShoppingCartUuid): PortException("The shopping cart with id $uuid is unknown.")

/**
 * Generell braucht in Spring nicht jede Controllermethode Exceptions behandeln.
 * Hierfür existieren explizite ExceptionHandler, welche Exception pro Typ behandeln, egal wo
 * so geworfen wurden.
 * 
 * @see LastLineOfDefenseErrorHandler
 */
@Controller
class ShoppingCartController(private val shoppingCartService: ShoppingCartService) {

    @RequestMapping(path = ["/shoppingcart/{uuid}"], method = [RequestMethod.GET])
    fun getShoppingCart(@PathVariable uuid: UUID): ResponseEntity<ShoppingCartDto> {
        val shoppingCartUuid = ShoppingCartUuid(uuid)

        return shoppingCartService.showShoppingCart(shoppingCartUuid)
                .map { shoppingCart -> ResponseEntity.ok(ShoppingCartDto.fromDomain(shoppingCart)) }
                .orElse( ResponseEntity.notFound().build())
    }

    @RequestMapping(path = ["/shoppingcart"], method = [RequestMethod.POST])
    fun postNewShoppingCart(): ResponseEntity<ShoppingCartDto> {
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
    fun putProductToShoppingCart(@PathVariable uuid: UUID, @RequestBody putProductDto: PutProduct): ResponseEntity<ShoppingCartDto> {
        val shoppingCartUuid = ShoppingCartUuid(uuid)

        return if(putProductDto.sku != null && putProductDto.quantity != null) {
            val sku = SKU(putProductDto.sku)
            val quantity = Quantity(putProductDto.quantity)

            shoppingCartService.putProductIntoShoppingCart(shoppingCartUuid, sku, quantity)
                    .map { shoppingCart ->
                        ResponseEntity.ok(ShoppingCartDto.fromDomain(shoppingCart))
                    }
                    .orElseThrow {
                        ShoppingCartNotFoundException(shoppingCartUuid)
                    }
        } else {
            ResponseEntity.badRequest().build();
        }
    }
}