package shoppingCart.ports.rest.dto

data class PutProduct(
    val sku: String?,
    val quantity: Int?
)

data class GetProduct(
    val sku: String,
    val name: String
)

data class GetItem(
    val quantity: Int,
    val product: GetProduct
)