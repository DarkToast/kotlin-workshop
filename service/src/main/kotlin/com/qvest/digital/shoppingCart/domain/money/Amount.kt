package com.qvest.digital.shoppingCart.domain.money

import com.qvest.digital.shoppingCart.domain.DomainException

class MaximumShoppingCardAmountExceededException(value: Int) :
    DomainException("The maximum shopping card amount of 300,00€ exceeded. Actual: '$value'")

data class Amount(val euro: Int, val cent: Int) : Money(euro, cent) {
    init {
        if (valueInCent > 30000) throw MaximumShoppingCardAmountExceededException(valueInCent)
    }

    operator fun plus(money: Money): Amount {
        val value = this.valueInCent + money.valueInCent
        return Amount(value / 100, value % 100)
    }
}