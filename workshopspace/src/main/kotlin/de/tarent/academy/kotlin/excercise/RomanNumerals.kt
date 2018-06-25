package de.tarent.academy.kotlin.excercise

class RomanNumerals {

    fun convert(romanNumber: String): Int {

        fun step(value: Int, romanNumber: String): Int {
            return if (romanNumber.length <= 1) {
                value
            } else {
                step(when (romanNumber) {
                    "I" -> 1
                    "V" -> 5
                    "X" -> 10
                    "L" -> 50
                    "C" -> 100
                    "D" -> 500
                    "M" -> 1000
                    else -> 0
                } + value, romanNumber.drop(1))
            }
        }

        return step(0, romanNumber)
    }

}