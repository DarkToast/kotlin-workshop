package de.tarent.ciwanzik.shoppingCart.ports

import de.tarent.ciwanzik.shoppingCart.ports.driven.productService.ProductRepositoryPort
import de.tarent.ciwanzik.shoppingCart.ports.driven.database.ShoppingCartRepositoryPort
import de.tarent.ciwanzik.shoppingCart.ports.driven.database.ShoppingCartJPARepository
import de.tarent.ciwanzik.shoppingCart.ports.driven.database.JpaShoppingCartRepository
import de.tarent.ciwanzik.shoppingCart.ports.driven.productService.CachingProductRepository
import de.tarent.ciwanzik.shoppingCart.ports.driven.productService.MockedProductRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class SpringPortsConfig {

    @Bean
    open fun productRepository(): ProductRepositoryPort {
        return CachingProductRepository(MockedProductRepository())
    }

    @Bean
    open fun shoppingCartRepository(jpaRepo: ShoppingCartJPARepository): ShoppingCartRepositoryPort {
        return JpaShoppingCartRepository(jpaRepo)
    }
}