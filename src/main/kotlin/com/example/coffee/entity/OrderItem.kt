package com.example.coffee.entity

import com.example.coffee.constant.Category
import com.example.coffee.dto.OrderItemDto
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "order_items")
data class OrderItem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var orderItemId: Long? = null,

    @jakarta.persistence.ManyToOne
    @JoinColumn(name = "product_id")
    var product: Product? = null,

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    var order: Order? = null,

    @Enumerated(EnumType.STRING)
    var category: Category? = Category.COFFEE_BEAN_PACKAGE,

    var price: Long? = null,
    var quantity: Int? = null,

    @CreationTimestamp
    var createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null,
)