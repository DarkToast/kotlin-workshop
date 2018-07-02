package de.tarent.academy.kotlin.session2

import javax.inject.Inject

class Simple

class Verbose (x: Int) {
}

class Account(val name: String, password: String = "s3cr3t", private val salt: String = "salt") {
    val hashedPassword: String

    init {
        hashedPassword = password + salt
    }

    constructor(name: String, password: String = "SuperGeheimesPasswort", salt: Int = 10): this(name, password, salt.toString()) {
        println("Hallo Welt")
    }

    fun hashSomething(value: String): String {
        return value + salt
    }
}


data class FirstName(val value: String)
data class LastName(val value: String)
data class Salary(val value: Int)


data class Employee(
    val firstName: FirstName,
    val lastName: LastName,
    val salary: Salary = Salary(50000)
)



fun main(args: Array<String>) {
    val name1 = FirstName("Max")
    val name2 = FirstName("Max")

    val max = Employee(
        FirstName("Max"),
        LastName("Mustermann"),
        Salary(40000)
    )


    println(name1 == name2)
    println(name1)


    val increasedMax = max.copy(salary = Salary(45000))

    println(increasedMax)
}