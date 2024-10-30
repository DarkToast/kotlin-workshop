package shoppingCart.ports.rest.dto

data class Failure(
    val timestamp: String,
    val status: Int,
    val reason: String,
    val message: String,
    val path: String
)