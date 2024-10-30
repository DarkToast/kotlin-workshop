package shoppingCart.ports

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import shoppingCart.ports.productService.CachingProductRepository
import shoppingCart.ports.productService.MockedProductRepository
import shoppingCart.ports.productService.ProductRepositoryPort

@Configuration
class SpringPortsConfig {

    @Bean
    fun productRepository(): ProductRepositoryPort {
        return CachingProductRepository(MockedProductRepository())
    }
}