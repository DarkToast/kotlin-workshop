package techtalk


data class FirstName(val firstname: String)

data class LastName(val lastName: String)

data class Salary(val salary: Int)

data class Employee(
    val firstName: FirstName,
    val lastName: LastName,
    val salary: Salary = Salary(50000)
)


val employee = Employee(
    FirstName("Max"),
    LastName("Mustermann")
)


// Equality
val equal = FirstName("Max") == FirstName("Max")

// Immutable copy   Caution: Only reference immutability!
val updatedEmployee = employee.copy(
    lastName = LastName("Musterfrau")
)
