package shoppingCart.domain.money

import shoppingCart.domain.DomainException
import shoppingCart.domain.Quantity

class TooHighPriceException(value: Int) : DomainException("Price must not exceed 120,00€. Actual: '$value'")

data class Price(val euro: Int, val cent: Int) : Money<Price>(euro, cent) {
    constructor(cent: Int) : this(cent / 100, cent % 100)

    init {
        if (valueInCent > 12000) throw TooHighPriceException(valueInCent)
    }

    override fun plus(money: Money<*>): Price {
        val value = this.valueInCent + money.valueInCent
        return Price(value / 100, value % 100)
    }

    operator fun times(quantity: Quantity): Amount = (1..quantity.value)
        .fold(Amount(0, 0)) { amount, _ -> amount + this }
}