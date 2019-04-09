package shoppingCart.application

import java.lang.RuntimeException

abstract class ApplicationException(message: String): RuntimeException(message)