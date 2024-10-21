package com.example.coffee.entity

import com.example.coffee.constant.OrderStatus
import com.example.coffee.dto.OrderDto
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var orderId: Long? = null,

    var email: String? = null,
    var address: String? = null, // 배송지 주소
    var postcode: String? = null,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    var orderItems: MutableList<OrderItem>? = mutableListOf(),

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ACCEPTED'")
    var orderStatus: OrderStatus? = null,

    @CreationTimestamp
    var createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null
)