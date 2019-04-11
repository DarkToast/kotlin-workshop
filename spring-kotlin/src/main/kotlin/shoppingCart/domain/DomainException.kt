package de.tarent.ciwanzik.shoppingCart.domain

import java.lang.RuntimeException

abstract class DomainException(message: String): RuntimeException(message)