package shoppingCart.ports

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import shoppingCart.ports.driven.database.JpaShoppingCartRepository
import shoppingCart.ports.driven.database.ShoppingCartJPARepository
import shoppingCart.ports.driven.database.ShoppingCartRepositoryPort
import shoppingCart.ports.driven.productService.CachingProductRepository
import shoppingCart.ports.driven.productService.MockedProductRepository
import shoppingCart.ports.driven.productService.ProductRepositoryPort

@Configuration
class SpringPortsConfig {

    @Bean
    fun productRepository(): ProductRepositoryPort {
        return CachingProductRepository(MockedProductRepository())
    }

    @Bean
    fun shoppingCartRepository(jpaRepo: ShoppingCartJPARepository): ShoppingCartRepositoryPort {
        return JpaShoppingCartRepository(jpaRepo)
    }
}