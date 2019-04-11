package de.tarent.ciwanzik.shoppingCart.ports

import java.lang.RuntimeException

abstract class PortException(message: String): RuntimeException(message)