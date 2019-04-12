package oneday.oop

// Data classes bilden value objects ab. In Java ähnlich zu PoJos
// Jedes Value ist automatisch von außen zugreifbar, aber immutable (val).
data class FirstName(val value: String)

data class LastName(val value: String)

data class Employee(
    val firstName: FirstName,
    val lastName: LastName
)

// Data classes lassen sich wie andere Objekte auch über ihren Konstruktor erzeugen
val max = Employee(
    FirstName("Max"),
    LastName("Mustermann")
)

val max2 = Employee(
    FirstName("Max"),
    LastName("Mustermann")
)

// Data classes bringen eine automatische equals und hashCode Methode mit.
// Der Ausdruck hier ist `true`
val equal = max == max2

// Ebenfalls bringen sie eine `copy` Methode mit, womit sich leicht ein neues
// Objekt erzeugen lässt:
val marriagedEmployee = max.copy(
    lastName = LastName("Musterfrau")
)