package advanced.functional

import io.kotest.core.spec.style.FeatureSpec
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

fun fulfillOrder(placedOrder: PlacedOrder, fulfilledQuantity: Int): FulfilledOrder {
    println("-> Fulfill order")
    val report = sendEmail(placedOrder.customer, "order fulfilled")

    println("-> Order fulfilled")
    return FulfilledOrder(placedOrder, report, fulfilledQuantity)
}

/**
 * Diese kleine customer journey ist vollgestopft mit Seiteneffekten in Form von Exceptions.
 * Einige sind eigentlich Validierungsfehler, andere Fehler weil ggf. Umsysteme nicht vorhanden sind
 * oder gesuchte Daten nicht gefunden wurden.
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
class MonadsSpec : FeatureSpec({
    feature("Customer journey") {
        val validCustomer: ValidCustomer

        try {
            val unregisteredCustomer = createCustomer("Max", "Musterfrau", "ich@inter.net")
            validCustomer = validateToken(unregisteredCustomer, "some wrong stuff")
        } catch(e: Exception) {
            println("Ouh oh! Something went wrong... ${e.message}")
            return@feature
        }

        try {
            val placedOrder = placeOrder(4, 10, validCustomer)
            val fulfilledOrder = fulfillOrder(placedOrder, 8)

            println("Order was fulfilled!")
        } catch (e: Exception) {
            println("Ouh oh! Something went wrong on order... ${e.message}")
            return@feature
        }

    }

})