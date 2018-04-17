package techtalk

import org.jetbrains.annotations.Contract

class Customer() {
    class Contract() {
        fun getBaseFee(): Double? = 47.11
    }

    fun getContract(): Contract? = Contract()
}


fun main(args: Array<String>) {
    // var vs. val
    var mutable: String = "Hallo"

    val immutable: String = "Welt!"


    // Nullable vs. NonNullable types
    var nullable: String? = null

    var notNullable: String = "Hallo"


    // Implicit type cast after a null check
    if(nullable != null) {
        notNullable = nullable
    }

    // Explicit null check operator `?.` and the elvis operator `?:` to define a default value
    val baseFee: Double = Customer().getContract()?.getBaseFee() ?: 0.0


    // Explicit type cast nullable -> non nullable type. Can cause NPEs!
    val baseFee2: Double = Customer().getContract()!!.getBaseFee()!!
}




