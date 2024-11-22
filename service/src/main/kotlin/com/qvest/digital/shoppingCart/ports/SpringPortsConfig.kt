package com.qvest.digital.shoppingCart.ports

import com.qvest.digital.shoppingCart.ports.productService.CachingProductRepository
import com.qvest.digital.shoppingCart.ports.productService.MockedProductRepository
import com.qvest.digital.shoppingCart.ports.productService.ProductRepositoryPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringPortsConfig {

    @Bean
    fun productRepository(): ProductRepositoryPort {
        return CachingProductRepository(MockedProductRepository())
    }
}