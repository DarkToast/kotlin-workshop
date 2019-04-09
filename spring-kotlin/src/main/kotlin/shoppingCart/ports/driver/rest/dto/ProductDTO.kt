package shoppingCart.ports.driver.rest.dto

data class PutProduct(
    val sku: String?,
    val quantity: Int?
)

data class GetProduct(
    val sku: String,
    val name: String
)