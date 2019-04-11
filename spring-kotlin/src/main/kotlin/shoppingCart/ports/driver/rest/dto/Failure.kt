package de.tarent.ciwanzik.shoppingCart.ports.driver.rest.dto

data class Failure (
    val timestamp: String,
    val status: Int,
    val reason: String,
    val message: String,
    val path: String
);