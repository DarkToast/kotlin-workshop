package shoppingCart.ports.driver.rest


import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import shoppingCart.application.ApplicationException
import shoppingCart.domain.DomainException
import shoppingCart.ports.PortException
import shoppingCart.ports.driver.rest.dto.Failure
import java.time.LocalDateTime

@ControllerAdvice
class LastLineOfDefenseErrorHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(ex: IllegalArgumentException, request: HttpServletRequest): ResponseEntity<Failure> {
        val now = LocalDateTime.now().toString()
        val message = ex.message ?: ""
        val error = Failure(now, BAD_REQUEST.value(), BAD_REQUEST.reasonPhrase, message, request.requestURI)

        return ResponseEntity(error, HttpHeaders(), BAD_REQUEST)
    }

    @ExceptionHandler(value = [DomainException::class, ApplicationException::class, PortException::class])
    fun handleDomainException(ex: RuntimeException, request: HttpServletRequest): ResponseEntity<Failure> {
        return ResponseEntity.badRequest().build()
    }
}