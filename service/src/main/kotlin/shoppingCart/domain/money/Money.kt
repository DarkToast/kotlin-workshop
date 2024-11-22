package shoppingCart.domain.money

sealed class Money(euro: Int, cent: Int) {
    val valueInCent = euro * 100 + cent

    init {
        if (euro < 0 || cent < 0) throw IllegalArgumentException("Money elements must not be negative.")
        if (cent > 99) throw IllegalArgumentException("Cent must not exceed 99")
    }
}

