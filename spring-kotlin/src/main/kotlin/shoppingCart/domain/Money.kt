package shoppingCart.domain

class TooHighPriceException(value: Int): DomainException("Price must not exceed 120,00€. Actual: '$value'")

class MaximumShoppingCardAmountExceededException(value: Int):
        DomainException("The maximum shopping card amount of 300,00€ exceeded. Actual: '$value'")

class Price(euro: Int, cent: Int) {
    val valueInCent = 0
    fun plus(price: Price): Price = TODO()
    fun times(quantity: Quantity): ShoppingCartAmount = TODO()
}

class ShoppingCartAmount(euro: Int, cent: Int) {
    val valueInCent = 0
    fun plus(amount: ShoppingCartAmount): ShoppingCartAmount = TODO()
}