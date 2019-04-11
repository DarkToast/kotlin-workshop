package shoppingCart.ports

import shoppingCart.ports.driven.productService.ProductRepositoryPort
import shoppingCart.ports.driven.database.ShoppingCartRepositoryPort
import shoppingCart.ports.driven.database.ShoppingCartJPARepository
import shoppingCart.ports.driven.database.JpaShoppingCartRepository
import shoppingCart.ports.driven.productService.CachingProductRepository
import shoppingCart.ports.driven.productService.MockedProductRepository
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