package com.qvest.digital.shoppingCart.ports

import com.qvest.digital.shoppingCart.ports.product.CachingAdapter
import com.qvest.digital.shoppingCart.ports.product.MockedAdapter
import com.qvest.digital.shoppingCart.ports.product.ProductPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Neben der direkten Annotation an den zu instantiierenden Klassen, können auch Konfigurationen
 * in einer mit {@link Configuration} annotierenden zusammengefasst sind.
 *
 * Als Beispiel wird hier wird der CachingAdapter instanziiert und als Bean registriert.
 */
@Configuration
class SpringPortsConfig {

    @Bean
    fun productRepository(): ProductPort {
        return CachingAdapter(MockedAdapter())
    }
}