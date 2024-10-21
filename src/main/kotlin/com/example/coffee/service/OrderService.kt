package com.example.coffee.service

import com.example.coffee.dto.OrderDto
import com.example.coffee.entity.Order

interface OrderService {
    fun save(order: OrderDto): Order
    fun orderList(): List<OrderDto>
    fun findByEmail(email: String): List<OrderDto>
    fun update(orderId: Long, dto: OrderDto): OrderDto
    fun delete(orderId: Long)
}