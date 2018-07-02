package session3

data class FirstName(val value: String)
data class LastName(val value: String)
data class EmailToken(val token: String) {
    fun isValid() = true
}

/**
 * Sealed classes stehen niemals für sich alleine, sondern sind immer ein Obertyp für eine begrenzte Anzahl
 * an Ableitungen. Die Ableitungen müssen dabei in der selben Kotlindatei wie die `sealed class` definiert werden.
 *
 * Damit ergibt sich eine flache Hierchie, die in sich geschlossen ist und dabei eine wohl definierte Anzahl ODER-
 * verknüpfbarer Typen bereitstellt.
 */
sealed class Email
data class UnverifiedEmail(val address: String): Email()
data class VerifiedEmail(val address: String): Email()

/**
 * In Verbindung mit `when` ermöglicht uns eine `sealed class` einen eleganten Kontrollfluss auf Typenebene.
 * Denn bei einem `when` kann der Kotlin Compiler genau sagen, ob alle möglichen Ausprägungen der Oberklasse abgedeckt
 * sind. Sind sie es nicht, so wird uns der Compiler einen Fehler werfen.
 *
 * Das kann einen Vorteil bringen, wenn wir unsere Domainlogik auf diese Typen abbilden. Somit können wir direkt auf
 * Typsystemebene unsere Logik implementieren und uns die Validierungsarbeit vom Compiler übernehmen lassen.
 *
 * Das heißt weniger Tetss, weniger manuelle Validierung und Abzweigungen.
 */
fun isVerifiedMail(email: Email): Boolean = when(email) {
    is UnverifiedEmail -> false
    is VerifiedEmail -> true
}

/**
 * Beispiel: Wir definieren uns einen `Customer`, der auf der bereits definieren Email-Hierachie aufsetzt.
 *  Customer hat zwei Ausprägungen:
 *      - ActiveCustomer      hat eine verifizierte Email
 *      - LimitesCustomer     hat eine neue, unverifizierte Email.
 */
sealed class Customer
data class ActiveCustomer(val firstName: FirstName, val lastName: LastName, val email: VerifiedEmail): Customer()
data class LimitedCustomer(val firstName: FirstName, val lastName: LastName, val email: UnverifiedEmail): Customer()

/**
 * Generell können wir mit `when` wieder überprüfen, ob ein generischer `Customer` aktiv ist oder nicht. Einzig und allein
 * anhand des Typs. Aufgrund der `sealed class` können wir jederzeit sicher sein, dass wir alle Verzweigungen behandelt haben.
 */
fun isActive(customer: Customer) = when(customer) {
    is ActiveCustomer -> true
    is LimitedCustomer -> false
}

/**
 * Mit unseren Typsystem können wir auch die Domainlogik direkt anwenden. Ein `activateCustomer` kann nur mit einem `
 * LimitedCustomer` aufgerufen werden. Ein `ActiveCustomer` ist weder möglich, noch sinnvoll. Zurück wird ein `Customer`
 * gegeben. Ob dieser nun erfolgreich aktiviert wurde oder nicht, sagt uns allein seine Typausprägung.
 */
fun activateCustomer(customer: LimitedCustomer, token: EmailToken): Customer {
    return if(token.isValid()) {
        ActiveCustomer(customer.firstName, customer.lastName, VerifiedEmail(customer.email.address))
    } else {
        customer
    }
}