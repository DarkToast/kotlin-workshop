package de.tarent.ciwanzik.shoppingCart.domain

data class SKU(val value: String)

data class Name(val value: String) {
    private val regex = "[\\w\\düöäÜÖÄß&]{1,40}".toRegex()

    init {
        if(!regex.matches(value)) throw IllegalArgumentException("A Name must contains 1 to 40 alphanumeric characters.")
    }

    override fun toString(): String {
        return value
    }
}


data class Product(val sku: SKU, val price: Price, val name: Name)