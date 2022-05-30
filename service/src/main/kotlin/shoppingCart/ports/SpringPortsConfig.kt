package shoppingCart.ports

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import shoppingCart.ports.driven.productService.CachingProductRepository
import shoppingCart.ports.driven.productService.MockedProductRepository
import shoppingCart.ports.driven.productService.ProductRepositoryPort

@Configuration
class SpringPortsConfig {

    @Bean
    fun productRepository(): ProductRepositoryPort {
        return CachingProductRepository(MockedProductRepository())
    }
}