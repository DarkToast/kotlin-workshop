package de.tarent.academy.kotlin

class Calculator {

    fun sum(list: List<Int>): Int {

        fun step(accumulator: Int, list: List<Int>): Int {
            return if(list.isEmpty()) {
                accumulator
            } else {
                step(accumulator + list.first(), list.drop(1))
            }
        }

        return step(0, list)
    }
}