package de.tarent.ciwanzik.shoppingCart.ports.driver.rest

import de.tarent.ciwanzik.shoppingCart.ports.driver.rest.dto.Failure
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import java.lang.IllegalArgumentException
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
}