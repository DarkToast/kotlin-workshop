package cheatsheets


data class FirstName(val value: String)

data class LastName(val value: String)

data class Salary(val value: Int)

data class Employee(
        val firstName: FirstName,
        val lastName: LastName,
        val salary: Salary = Salary(50000)
)

val max = Employee(
        FirstName("Max"),
        LastName("Mustermann")
)

val yvonne = Employee(
        FirstName("Yvonne"),
        LastName("Musterfrau"),
        Salary(98000)
)


// Equality
val equal = FirstName("Max") == FirstName("Max")

val updatedEmployee = max.copy(
    lastName = LastName("Musterfrau")
)
