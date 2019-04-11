package shoppingCart.ports.driver.rest

import shoppingCart.application.ApplicationException
import shoppingCart.application.ProductNotFoundException
import shoppingCart.domain.DomainException
import shoppingCart.ports.PortException
import shoppingCart.ports.driver.rest.dto.Failure
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.RuntimeException
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
open class LastLineOfDefenseErrorHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(ex: IllegalArgumentException, request: HttpServletRequest): ResponseEntity<Failure> {
        val now = LocalDateTime.now().toString()
        val message = ex.message ?: ""
        val error = Failure(now, BAD_REQUEST.value(), BAD_REQUEST.reasonPhrase, message, request.getRequestURI())

        return ResponseEntity(error, HttpHeaders(), BAD_REQUEST)
    }

    @ExceptionHandler(value = [DomainException::class, ApplicationException::class, PortException::class])
    fun handleDomainException(ex: RuntimeException, request: HttpServletRequest): ResponseEntity<Failure> {
        return ResponseEntity.badRequest().build()
    }
}