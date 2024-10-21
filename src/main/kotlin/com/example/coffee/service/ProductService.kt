package com.example.coffee.service

import com.example.coffee.constant.Category
import com.example.coffee.dto.ProductDto
import com.example.coffee.entity.Product

interface ProductService {
    fun getProductsByCategory(category: Category): List<ProductDto>
    fun getProducts(): List<ProductDto>
    fun createProduct(dto: ProductDto): Product
    fun readProduct(productId: Long): Product
    fun updateProduct(productId: Long, dto: ProductDto): Product
    fun deleteProduct(productId: Long)
}