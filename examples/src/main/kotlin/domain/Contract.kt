package domain

class Customer(
    val name: String,
    val surname: String,
    val email: String?,
    val age: Int,
    val contracts: List<Contract>
)

enum class ContractType {
    STROM,
    GAS
}

class Contract (
    val type: ContractType,
    val abschlag: Double,
    val durchschnittVerbrauch: Double,
    val baseFee: Double,
    val kWhPrice: Double
)