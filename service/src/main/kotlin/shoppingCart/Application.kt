package shoppingCart

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}