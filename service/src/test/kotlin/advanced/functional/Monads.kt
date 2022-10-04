package advanced.functional

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.lang.Thread.sleep
import java.time.OffsetDateTime
import java.util.UUID

data class Token(val secret: String)

data class UnregisteredCustomer(
    val id: UUID,
    val name: String,
    val surname: String,
    val email: String?,
    val verificationToken: Token
)

data class ValidCustomer(
    val id: UUID,
    val name: String,
    val surname: String,
    val email: String
)

data class EmailReport(
    val email: String,
    val sendDate: OffsetDateTime
)

data class PlacedOrder(
    val productId: Int,
    val quantity: Int,
    val orderMail: EmailReport,
    val customer: ValidCustomer
)

data class FulfilledOrder(
    val placedOrder: PlacedOrder,
    val fulfillmentMail: EmailReport,
    val fulfilledQuantity: Int,
)

sealed class Failure
data class ValidationFailure(val element: String, val failureMessage: String) : Failure()
data class SystemFailure(val failureMessage: String) : Failure()


val tokenStore: MutableMap<UUID, Token> = mutableMapOf()

fun createCustomer(name: String, surname: String, email: String): UnregisteredCustomer {
    println("-> Creating new customer")

    if (name.length > 16) {
        throw IllegalArgumentException("The customer name it too long")
    }

    if (!email.contains("@")) {
        throw IllegalArgumentException("The email must contain an @")
    }

    val id = UUID.randomUUID()
    val token = Token(UUID.randomUUID().toString())
    tokenStore[id] = token

    println("-> Created new UnregisteredCustomer")
    return UnregisteredCustomer(id, name, surname, email, token)
}


fun validateToken(customer: UnregisteredCustomer, secret: String): ValidCustomer {
    println("-> Validate registration token")

    val token: Token = tokenStore[customer.id] ?: throw IllegalArgumentException("Could not find token by secret")

    if (customer.verificationToken != token) {
        throw IllegalArgumentException("Could not verify customer!")
    }

    if (customer.email == null) {
        throw IllegalArgumentException("Email is null! Could not validate customer")
    }

    println("-> Customer validated - Welcome ${customer.name} ${customer.surname}")
    return ValidCustomer(
        id = customer.id,
        name = customer.name,
        surname = customer.surname,
        email = customer.email
    )
}

fun sendEmail(customer: ValidCustomer, message: String): EmailReport {
    sleep(500)
    println("-> Sending email")
    if (customer.email.length % 2 == 0) {
        throw RuntimeException("Email could not sent!")
    }

    println("-> Email '$message' send to ${customer.email}")
    return EmailReport(customer.email, OffsetDateTime.now())
}

fun placeOrder(productId: Int, quantity: Int, customer: ValidCustomer): PlacedOrder {
    println("-> Placing order")
    if (productId > 10) {
        throw RuntimeException("Product could not be found!")
    }

    val report = sendEmail(customer, "Order placed!")
    println("-> Order placed")
    return PlacedOrder(productId, quantity, report, customer)
}

fun fulfillOrder(placedOrder: PlacedOrder, fulfilledQuantity: Int): Result {
    return try {
        println("-> Fulfill order")
        val report = sendEmail(placedOrder.customer, "order fulfilled")
        println("-> Order fulfilled")
        Result(null, FulfilledOrder(placedOrder, report, fulfilledQuantity))
    } catch (e: Throwable) {
        Result(SystemFailure(e.message ?: ""), null)
    }
}

/**
 * Diese kleine customer journey ist vollgestopft mit Seiteneffekten in Form von Exceptions.
 * Einige sind eigentlich Validierungsfehler, andere Fehler weil ggf. Umsysteme nicht vorhanden sind
 * oder gesuchte Daten nicht ge@Test funden wurden.
 *
 * Die Aufgabe besteht darin, sich eine Monade auszudenken, die entweder einen korrekten Wert oder einen Fehler
 * darstellt. Also ähnlich einem Nullable, nur das wir ein Entweder-Oder darstellen.
 *
 * Vorschlag: Result<Failure, ValidCustomer> --> Die Typen
 *
 * Generell ist es eine gute Idee den Urpsrung so weit wie möglich nachzuverfolgen.
 * Ihr könnt also auch die Methoden wie `createCustomer` so umbauen, das sie statt eines Wert oder einer Exception,
 * immer eine Monade zurück geben und den Fehlertyp selbst in der Hand halten.
 */
data class Result(private val failure: Failure?, private val value: Any?) {
    fun hasFailure(): Boolean = failure != null
    fun isSuccess(): Boolean = value != null

    fun get(): Any? = value
    fun getFailure(): Failure? = failure

    fun recover(handle: (Failure) -> Any): Any = TODO()
    fun map(transform: (Any) -> Any): Result = TODO()
    fun flatMap(transform: (Any) -> Result): Result = TODO()

    companion object {
        fun ofFailure(failure: Failure): Result = TODO()
        fun ofSuccess(success: Any): Result = TODO()
    }
}

@Disabled
class MonadsSpec {
    @Test
    fun `Can be created of failure`() {
        val result: Result = Result.ofFailure(SystemFailure("something went wrong"))
        assertTrue(result.hasFailure())
    }

    @Test
    fun `Can be created of success`() {
        val result = Result.ofSuccess(ValidCustomer(UUID.randomUUID(), "Hans", "Dampf", "ich@inter.net"))
        assertTrue(result.isSuccess())
    }

    /*
    @Test
    fun `Is generic in success`() {
        val result: Result<Failure, ValidCustomer> = Result.ofSuccess(ValidCustomer(UUID.randomUUID(), "Hans", "Dampf", "ich@inter.net"))
        assertTrue(result.isSuccess)
    }

    @Test
    fun `Is generic in failure`() {
        val result: Result<SystemFailure, ValidCustomer> = Result.ofFailure<ValidCustomer>(SystemFailure("something went wrong"))
        assertTrue(result.isSuccess())
    }
    */

    @Test
    fun `Value can be mapped`() {
        val result: Any? = Result.ofSuccess("Hallo Welt")
            .map { str -> (str as String).length }
            .map { len -> (len as Int) == 10 }
            .get()

        assertTrue(result is Boolean)
        assertTrue(result as Boolean)
    }

    @Test
    fun `Value can be flat mapped`() {
        val result = Result.ofSuccess("Hallo Welt")
            .flatMap { str -> Result.ofSuccess((str as String).length) }
            .flatMap { len -> Result.ofSuccess((len as Int) == 10) }
            .get()

        assertTrue(result is Boolean)
        assertTrue(result as Boolean)
    }

    @Test
    fun `recover gets the given success value`() {
        val result = Result.ofSuccess("Hallo Welt")
            .recover { _ -> "Foobar" }

        assertTrue(result is String)
        assertEquals("Hallo Welt", result)
    }

    @Test
    fun `recover gets the recover value`() {
        val result: String = Result.ofFailure(SystemFailure("System failure"))
            .recover { _ -> "Foobar" } as String

        assertTrue(result is String)
        assertEquals("Foobar", result)
    }


    @Test
    fun `with failure`() {
        assertDoesNotThrow {
            val unregisteredCustomer = createCustomer("Max", "Musterfrau", "ich@inter.net2")
            val validCustomer = validateToken(unregisteredCustomer, "some wrong stuff")
            val placedOrder = placeOrder(4, 10, validCustomer)
            val result = fulfillOrder(placedOrder, 8)

            assertTrue(result.hasFailure())
            assertTrue(result.getFailure() is SystemFailure)
            assertEquals("Email could not sent!", (result.getFailure() as SystemFailure).failureMessage)
        }
    }

    @Test
    fun `with success`() {
        assertDoesNotThrow {
            val unregisteredCustomer = createCustomer("Max", "Musterfrau", "ich@inter.net")
            val validCustomer = validateToken(unregisteredCustomer, "some wrong stuff")
            val placedOrder = placeOrder(4, 10, validCustomer)
            val result = fulfillOrder(placedOrder, 8)

            assertFalse(result.hasFailure())
            assertNull(result.getFailure())
            assertNotNull(result.get())
            assertEquals(8, (result.get() as FulfilledOrder).fulfilledQuantity)
            assertEquals(10, (result.get() as FulfilledOrder).placedOrder.quantity)
        }
    }
}