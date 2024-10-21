package com.example.coffee.service

import com.example.coffee.dto.OrderDto
import com.example.coffee.entity.Order
import com.example.coffee.entity.OrderItem
import com.example.coffee.entity.Product
import com.example.coffee.repository.OrderItemRepository
import com.example.coffee.repository.OrderRepository
import com.example.coffee.repository.ProductRepository
import jakarta.transaction.Transactional
import org.modelmapper.ModelMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class OrderServiceImpl(
    val orderRepository: OrderRepository,
    val orderItemRepository: OrderItemRepository,
    val modelMapper: ModelMapper,
    val productRepository: ProductRepository
): OrderService {
    override fun orderList(): List<OrderDto> {
        return orderRepository.findAll()
            .map { modelMapper.map(it, OrderDto::class.java) }
            .toList()
    }

    override fun save(orderDto: OrderDto): Order {
        val order = orderDto.orderId?.let { orderRepository.findByIdOrNull(it) } ?: Order()

        order.email = orderDto.email
        order.address = orderDto.address
        order.postcode = orderDto.postcode

        val existingOrderItems = order.orderItems ?: mutableListOf()

        orderDto.orderItems?.forEach { itemDto ->
            val product: Product = productRepository.findByIdOrNull(itemDto.productId)
                ?: throw NoSuchElementException("상품을 찾을 수 없습니다: id=${itemDto.productId}")

            val existingItem = existingOrderItems.find { it.product?.productId == product.productId }

            if (existingItem != null) {
                existingItem.quantity = itemDto.quantity
                existingItem.price = product.price
                existingItem.category = product.category
            } else {
                val newItem = OrderItem(
                    product = product,
                    order = order,
                    category = product.category,
                    price = product.price,
                    quantity = itemDto.quantity
                )
                existingOrderItems.add(newItem)
            }
        }
        order.orderItems = existingOrderItems

        return orderRepository.save(order)
    }

    override fun findByEmail(email: String): List<OrderDto> {
        return orderRepository.findByEmail(email)
            .map { modelMapper.map(it, OrderDto::class.java) }
            .toList()
    }

    override fun update(orderId: Long, dto: OrderDto): OrderDto { // OrderDto를 반환
        val order = orderRepository.findById(orderId).orElseThrow {
            NoSuchElementException("주문을 찾을 수 없습니다: id=$orderId")
        }

        order.email = dto.email ?: order.email
        order.address = dto.address ?: order.address
        order.postcode = dto.postcode ?: order.postcode

        val existingOrderItems = order.orderItems ?: mutableListOf()

        dto.orderItems?.let { orderItems ->
            if (orderItems.isNotEmpty()) {
                orderItems.forEach { itemDto ->
                    val existingItem = existingOrderItems.find { it.product?.productId == itemDto.productId }

                    if (existingItem != null) {
                        existingItem.quantity = itemDto.quantity
                        existingItem.price = itemDto.price
                        existingItem.category = itemDto.category
                    } else {
                        val newItem = OrderItem(
                            product = productRepository.findByIdOrNull(itemDto.productId)
                                ?: throw NoSuchElementException("상품을 찾을 수 없습니다: id=${itemDto.productId}"),
                            order = order,
                            category = itemDto.category,
                            price = itemDto.price,
                            quantity = itemDto.quantity
                        )
                        existingOrderItems.add(newItem)
                    }
                }

                val orderItemIds = orderItems.map { it.productId }.toSet()
                existingOrderItems.removeIf { it.product?.productId !in orderItemIds }
            }
        }

        order.orderItems = existingOrderItems

        val updatedOrder = orderRepository.save(order)
        return modelMapper.map(updatedOrder, OrderDto::class.java) // DTO로 변환하여 반환
    }

    override fun delete(orderId: Long) {
        return orderRepository.deleteById(orderId)
    }
}