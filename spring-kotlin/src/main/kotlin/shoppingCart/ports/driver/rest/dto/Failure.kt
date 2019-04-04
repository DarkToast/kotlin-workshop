package de.tarent.ciwanzik.shoppingCart.ports.driver.rest.dto

data class Failure (
    val timestamp: String,
    val status: Int,
    val error: String,
    val message: String,
    val path: String
);