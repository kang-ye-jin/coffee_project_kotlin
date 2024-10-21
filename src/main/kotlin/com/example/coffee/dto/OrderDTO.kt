package com.example.coffee.dto

data class OrderDto(
    var orderId: Long? = null,
    var email: String? = null,
    var address: String? = null,
    var postcode: String? = null,
    var orderItems: MutableList<OrderItemDto>? = mutableListOf()
)