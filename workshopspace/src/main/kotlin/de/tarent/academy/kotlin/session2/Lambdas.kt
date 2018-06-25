package de.tarent.academy.kotlin.session2

import session2.Employee
import session2.Salary
import session2.max
import session2.yvonne

fun main(args: Array<String>) {
    lambdas()
}

fun lambdas() {
    val aList: List<Int> = listOf(1, 2, 3, 4, 5)

    val mapFunction: (Int) -> String = {
        i: Int -> i.toString()
    }
    val mapFunction2 = { i: Int, j: Int ->
        i.toString() + "Hallo Welt"
    }

    fun mapMethod(i: Int): String {
        return i.toString() + "Hallo Welt"
    }

    val mappedList: List<String> = aList.map(mapFunction)

    aList.all { a: Int -> a % 2 == 0 }


}

typealias EmployeeMapper = (Employee) -> Employee

fun higherOrderFunction() {

    fun matchEmployees(employee: List<Employee>, matcher: (Employee) -> Boolean): Boolean {
        return employee.all(matcher)
    }

    val matcher: (Employee) -> Boolean = { e: Employee -> e.salary.value > 40000 }

    val list = listOf(max, yvonne)
    val match = matchEmployees(list, matcher)


    fun createSalaryIncrease(factor: Float): (Employee) -> Employee {

        return { employee ->
            val salary = Salary((employee.salary.value * factor).toInt())
            Employee(employee.firstName, employee.lastName, salary)
        }

    }


    val salaryIncrease2018: EmployeeMapper = createSalaryIncrease(1.2F)
    val salaryIncrease2019: EmployeeMapper = createSalaryIncrease(0.9F)

    val employees2018: List<Employee> = list.map(salaryIncrease2018)
    val employees2019: List<Employee> = employees2018.map(salaryIncrease2019)

}




























