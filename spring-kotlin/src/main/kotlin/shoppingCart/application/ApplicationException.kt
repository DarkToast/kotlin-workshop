package de.tarent.ciwanzik.shoppingCart.application

import java.lang.RuntimeException

abstract class ApplicationException(message: String): RuntimeException(message)