package shoppingCart.ports.driver.rest

import shoppingCart.application.ShoppingCartService
import shoppingCart.domain.Quantity
import shoppingCart.domain.SKU
import shoppingCart.domain.ShoppingCartUuid
import shoppingCart.ports.PortException
import shoppingCart.ports.driver.rest.dto.PutProduct
import shoppingCart.ports.driver.rest.dto.ShoppingCartDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.net.URI
import java.util.*

class ShoppingCartNotFoundException(uuid: ShoppingCartUuid): PortException("The shopping cart with id $uuid is unknown.")

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

    @RequestMapping(path = ["/shoppingcart/{uuid}"], method = [RequestMethod.PUT])
    fun putProductToShoppingCart(@PathVariable uuid: UUID, @RequestBody putProductDto: PutProduct): ResponseEntity<ShoppingCartDto> {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build()
    }
}