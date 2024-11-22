package com.qvest.digital.shoppingCart.ports.rest.dto

import java.net.URI

abstract class Linked<out T : Linked<T>> {
    val links: MutableMap<String, Link> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    fun addLink(name: String, method: Method, href: URI): T {
        links[name] = Link(name, method.toString(), href.toString())
        return this as T
    }
}

enum class Method {
    POST, GET, PUT, DELETE
}

data class Link(val name: String, val method: String, val href: String)