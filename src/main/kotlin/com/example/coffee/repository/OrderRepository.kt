package com.example.coffee.repository

import com.example.coffee.entity.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {
    fun findByEmail(email: String): List<Order>
}