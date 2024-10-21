package com.example.coffee.controller

import com.example.coffee.dto.OrderDto
import com.example.coffee.entity.Order
import com.example.coffee.service.OrderService
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/order")
class OrderRestController(
    val orderService: OrderService,
    private val modelMapper: ModelMapper
) {
    @GetMapping("/list")
    fun orderList() = orderService.orderList()

    @PostMapping("/create")
    fun createOrder(@RequestBody orderDto: OrderDto): ResponseEntity<OrderDto> {
        val order = orderService.save(orderDto)
        val responseDto = modelMapper.map(order, OrderDto::class.java)

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto)
    }

    @GetMapping("/list/email/{email}")
    fun emailOrderList(@PathVariable email: String): List<OrderDto> {
        return orderService.findByEmail(email)
    }

    @PutMapping("/update/{orderId}")
    fun updateOrder(@PathVariable orderId: Long, @RequestBody dto: OrderDto): ResponseEntity<OrderDto> {
        val updatedOrder = orderService.update(orderId, dto) // OrderDto를 반환받음
        return ResponseEntity.ok(updatedOrder) // HTTP 200 OK와 함께 업데이트된 주문 반환
    }

    @DeleteMapping("/delete/{orderId}")
    fun deleteOrder(@PathVariable orderId: Long): ResponseEntity<Void> {
        orderService.delete(orderId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}