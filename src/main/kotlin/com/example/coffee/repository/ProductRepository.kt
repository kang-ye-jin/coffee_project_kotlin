package com.example.coffee.repository

import com.example.coffee.constant.Category
import com.example.coffee.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun findByCategoryOrderByProductIdDesc(category: Category): List<Product>
}