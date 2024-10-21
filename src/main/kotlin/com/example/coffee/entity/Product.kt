package com.example.coffee.entity

import com.example.coffee.constant.Category
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "products")
data class Product (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var productId: Long? = null,
    var productName: String = "",

    @Enumerated(EnumType.STRING)
    var category: Category? = null,

    var price:Long = 0,
    var description: String = "",

    @CreationTimestamp
    var createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null,
)