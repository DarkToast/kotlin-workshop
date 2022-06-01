package shoppingCart.ports.driver.rest


import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import shoppingCart.application.ApplicationException
import shoppingCart.application.ProductNotFoundException
import shoppingCart.domain.DomainException
import shoppingCart.ports.PortException
import shoppingCart.ports.driver.rest.dto.Failure
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class LastLineOfDefenseErrorHandler {

    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(ex: IllegalArgumentException, request: HttpServletRequest): ResponseEntity<Failure> {
        logger.warn(ex) { "For request with illegal arguments. Returning ${BAD_REQUEST.name}" }

        val now = LocalDateTime.now().toString()
        val message = ex.message ?: ""
        val error = Failure(now, BAD_REQUEST.value(), BAD_REQUEST.reasonPhrase, message, request.requestURI)

        return ResponseEntity(error, HttpHeaders(), BAD_REQUEST)
    }

    @ExceptionHandler(value = [DomainException::class, ApplicationException::class, PortException::class])
    fun handleDomainException(ex: RuntimeException, request: HttpServletRequest): ResponseEntity<Failure> {
        val httpStatus = when (ex) {
            is ProductNotFoundException -> NOT_FOUND
            is ShoppingCartNotFoundException -> NOT_FOUND
            else -> BAD_REQUEST
        }

        logger.warn(ex) { "Got domain explicit failure. Returning ${httpStatus.name}" }

        val now = LocalDateTime.now().toString()
        val message = ex.message ?: ""
        val error = Failure(now, httpStatus.value(), httpStatus.reasonPhrase, message, request.requestURI)

        return ResponseEntity(error, HttpHeaders(), httpStatus)
    }
}