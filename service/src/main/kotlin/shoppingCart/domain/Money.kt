package shoppingCart.domain

class TooHighPriceException(value: Int) : DomainException("Price must not exceed 120,00€. Actual: '$value'")

class MaximumShoppingCardAmountExceededException(value: Int) :
    DomainException("The maximum shopping card amount of 300,00€ exceeded. Actual: '$value'")

sealed class Money<T: Money<T>> (euro: Int, cent: Int) {
    val valueInCent = euro * 100 + cent

    init {
        if(euro < 0 || cent < 0) throw IllegalArgumentException("Money elements must not be negative.")
        if(cent > 99) throw IllegalArgumentException("Cent must not exceed 99")
    }

    abstract operator fun plus(money: Money<*>): T
}

data class Price(val euro: Int, val cent: Int): Money<Price>(euro, cent) {
    init {
        if(valueInCent > 12000) throw TooHighPriceException(valueInCent)
    }

    override fun plus(money: Money<*>): Price {
        val value = this.valueInCent + money.valueInCent
        return Price(value / 100, value % 100)
    }

    operator fun times(quantity: Quantity): Amount = (1 .. quantity.value)
            .fold(Amount(0, 0)) { amount, _ -> amount + this }
}

data class Amount(val euro: Int, val cent: Int): Money<Amount>(euro, cent) {
    init {
        if(valueInCent > 30000) throw MaximumShoppingCardAmountExceededException(valueInCent)
    }

    override fun plus(money: Money<*>): Amount {
        val value = this.valueInCent + money.valueInCent
        return Amount(value / 100, value % 100)
    }
}