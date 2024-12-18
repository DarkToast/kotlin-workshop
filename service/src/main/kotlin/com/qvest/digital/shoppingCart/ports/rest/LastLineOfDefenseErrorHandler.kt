package com.qvest.digital.shoppingCart.ports.rest


import com.qvest.digital.shoppingCart.DomainException
import com.qvest.digital.shoppingCart.application.ProductNotFoundException
import com.qvest.digital.shoppingCart.application.ShoppingCartNotFoundException
import com.qvest.digital.shoppingCart.domain.MaximumProductCountExceededException
import com.qvest.digital.shoppingCart.ports.rest.dto.Failure
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

/**
 * Mit {@link ControllerAdvice} erstellt man einen allgemeinen Exception-Handler für alle Controller.
 * Also alles, was jenseits der Controller-Ebene noch als Exception auftreten kann, kann an dieser Stelle
 * behandelt werden.
 *
 * Eigenet sich besonders dafür jeden Fehler mit einer technologisch unabhängigen Fehlermeldung zu versehen,
 * um potenziellen Angreifern weniger Informationen zugänglich zu machen.
 *
 * Jede Methode bekommt die annotierte Exception als Parameter plus den allgemeinen
 * {@link HttpServletRequest} für weitere Informationen.  Erwartet wird, wie bei den Controllern
 * eine ResponseEntity.
 */
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

    @ExceptionHandler(value = [DomainException::class])
    fun handleDomainException(ex: DomainException, request: HttpServletRequest): ResponseEntity<Failure> {
        val httpStatus = when (ex) {
            is ProductNotFoundException -> NOT_FOUND
            is ShoppingCartNotFoundException -> NOT_FOUND
            is MaximumProductCountExceededException -> BAD_REQUEST
            else -> BAD_REQUEST
        }

        logger.warn(ex) { "Got domain explicit failure. Returning ${httpStatus.name}" }

        val now = LocalDateTime.now().toString()
        val message = ex.message ?: ""
        val error = Failure(now, httpStatus.value(), httpStatus.reasonPhrase, message, request.requestURI)

        return ResponseEntity(error, HttpHeaders(), httpStatus)
    }
}