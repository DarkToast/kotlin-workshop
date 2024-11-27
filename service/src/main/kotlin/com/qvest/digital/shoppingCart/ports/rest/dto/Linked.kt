package com.qvest.digital.shoppingCart.ports.rest.dto

import java.net.URI

/**
 * Als Basisklasse für Response-DTOs, um diese mit einem Linkobjekt zu erweitern.
 * DTO: Der abgeleitete DTO Typ.
 */
abstract class Linked<out DTO : Linked<DTO>> {
    // public @links must be public for Jackson to serialize it
    @Suppress("MemberVisibilityCanBePrivate")
    val links: MutableMap<String, Link> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    fun addLink(name: String, method: Method, href: URI): DTO {
        links[name] = Link(name, method, href)
        return this as DTO
    }
}

// Elements are used in further methods.
@Suppress("unused")
enum class Method {
    POST, GET, PUT, DELETE
}

data class Link(val name: String, val method: String, val href: String) {
    constructor(name: String, method: Method, href: URI) :
        this(name, method.toString(), href.toString())
}