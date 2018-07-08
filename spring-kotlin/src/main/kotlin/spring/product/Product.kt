package spring.product

import org.springframework.data.annotation.Id

data class SKU(val value: String) {
    init {
        if (value.isEmpty() || !value.matches(Regex("^[\\w\\d]+$"))) {
            throw IllegalArgumentException("$value is not a valid sku")
        }
    }
}

data class ProductName(val name: String) {
    init {
        if (name.isEmpty()) {
            throw IllegalArgumentException("$name must not be empty")
        }
    }
}

data class EAN(val value: String) {
    init {
        if (value.isEmpty() || !value.matches(Regex("^[\\d]{8}\$|^[\\d]{13}\$"))) {
            throw IllegalArgumentException("$value is not a valid EAN")
        }
    }
}


data class Product(@Id val sku: SKU, val name: ProductName, val ean: EAN)

