package de.tarent.ciwanzik.shoppingCart.domain

data class SKU(val value: String)

data class Name(val value: String)

data class Product(val sku: SKU, val price: Price, val name: Name)