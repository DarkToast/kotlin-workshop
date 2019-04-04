package de.tarent.ciwanzik.shoppingCart.ports.driver.rest.dto

data class PutProduct(
    val sku: String?,
    val quantity: Int?
)