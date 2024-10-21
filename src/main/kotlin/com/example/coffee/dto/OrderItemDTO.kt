package com.example.coffee.dto

import com.example.coffee.constant.Category

data class OrderItemDto(
    var productId: Long? = null,
    var category: Category? = null,
    var price: Long? = null,
    var quantity: Int? = null,
)