package techtalk

import java.util.*


data class CustomerUuid(val customerUuid: UUID)
data class Token(val token: String)

sealed class Customer2
data class UnverifiedCustomer(val firstName: FirstName) : Customer2()
data class VerifiedCustomer(val firstName: FirstName, val token: Token): Customer2()

fun isVerified(customer: Customer2): Boolean = when(customer) {
    is UnverifiedCustomer -> false
    is VerifiedCustomer -> true

}


// sealed class hierarchy - or representing a limited set of possible values
sealed class Expression()
data class Const(val number: Double): Expression()
data class Sum(val e1: Expression, val e2: Expression) : Expression()
object NotANumber : Expression()

// 'when' expression must be exhaustive, add necessary 'NotANumber' branch or 'else' branch instead
fun eval(expr: Expression): Double = when(expr) {
    is Const -> expr.number
    is Sum -> eval(expr.e1) + eval(expr.e2)
    is NotANumber -> Double.NaN

    // the `else` clause is not required because we've covered all the cases
}

fun createCustomer(customer: Customer): Boolean = TODO()
fun deleteCustomer(customerUuid: CustomerUuid): Boolean = TODO()


sealed class Event
data class CustomerCreateEvent(val customer: Customer): Event()
data class CustomerDeleteEvent(val customerUuid: CustomerUuid): Event()

fun handleEvents(event: Event) = when(event) {
    is CustomerCreateEvent -> createCustomer(event.customer)
    is CustomerDeleteEvent -> deleteCustomer(event.customerUuid)
}