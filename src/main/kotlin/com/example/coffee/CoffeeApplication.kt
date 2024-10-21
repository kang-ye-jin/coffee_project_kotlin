package com.example.coffee

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class CoffeeApplication

fun main(args: Array<String>) {
    runApplication<CoffeeApplication>(*args)
}
