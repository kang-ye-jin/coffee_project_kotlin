package com.example.coffee.controller

import com.example.coffee.constant.Category
import com.example.coffee.dto.ProductDto
import com.example.coffee.entity.Product
import com.example.coffee.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/products")
class ProductRestController(val productService: ProductService) {
    @GetMapping("/list")
    fun productList(@RequestParam category: Category?) =
        if (category == null) productService.getProducts()
        else productService.getProductsByCategory(category)

    @PostMapping("/create")
    fun create(@RequestBody dto: ProductDto): ResponseEntity<Product> {
        val product = productService.createProduct(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(product)
    }

    @GetMapping("/read/{productId}")
    fun read(@PathVariable productId: Long): ResponseEntity<Product> {
        val product = productService.readProduct(productId)
        return ResponseEntity.status(HttpStatus.CREATED).body(product)
    }

    @PutMapping("/update/{productId}")
    fun update(@PathVariable productId: Long, @RequestBody dto: ProductDto): ResponseEntity<Product> {
        val product = productService.updateProduct(productId, dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(product)
    }

    @DeleteMapping("/delete/{productId}")
    fun delete(@PathVariable productId: Long): ResponseEntity<Void> {
        productService.deleteProduct(productId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
