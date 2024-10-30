package shoppingCart.domain

import shoppingCart.domain.money.Price

data class Name(val value: String) {
    private val regex = "[\\w\\düöäÜÖÄß&]{1,40}".toRegex()

    init {
        if (!regex.matches(value)) throw IllegalArgumentException("A Name must contains 1 to 40 alphanumeric characters.")
    }

    override fun toString(): String {
        return value
    }
}

data class SKU(val value: String) {
    private val regex = "[\\w\\d]{1,20}".toRegex()

    init {
        if (!regex.matches(value)) throw IllegalArgumentException("A SKU must contains 1 to 20 alphanumeric characters.")
    }

    override fun toString(): String {
        return value
    }
}

data class Product(val sku: SKU, val price: Price, val name: Name)