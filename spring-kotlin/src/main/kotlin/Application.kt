package de.tarent.ciwanzik

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
open class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}