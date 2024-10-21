package com.example.coffee.service

import com.example.coffee.constant.Category
import com.example.coffee.dto.ProductDto
import com.example.coffee.entity.Product
import com.example.coffee.repository.ProductRepository
import org.modelmapper.ModelMapper
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(
    val productRepository: ProductRepository,
    val modelMapper: ModelMapper
): ProductService {
    override fun getProductsByCategory(category: Category): List<ProductDto> {
        return productRepository.findByCategoryOrderByProductIdDesc(category)
            .map{modelMapper.map(it, ProductDto::class.java)}
            .toList()
    }

    override fun getProducts(): List<ProductDto> {
        val sort = Sort.by(Sort.Direction.ASC, "ProductId")
        return productRepository.findAll(sort)
            .map{modelMapper.map(it, ProductDto::class.java)}
            .toList()
    }

    override fun createProduct(dto: ProductDto): Product {
        val product = Product(
            productName = dto.productName ?: "",
            category = Category.valueOf(dto.category ?: "COFFEE_BEAN_PACKAGE"),
            price = dto.price ?: 0,
            description = dto.description ?: ""
        )
        return productRepository.save(product)
    }

    override fun readProduct(productId: Long): Product {
        return productRepository.findById(productId).orElseThrow {
            throw NoSuchElementException("상품을 찾을 수 없습니다: id=$productId")
        }
    }

    override fun updateProduct(productId: Long, dto: ProductDto): Product {
        val product = productRepository.findById(productId).orElseThrow {
            throw NoSuchElementException("상품을 찾을 수 없습니다: id=$productId")
        }

        product.productName = dto.productName ?: product.productName
        product.category = dto.category?.let {
            try {
                Category.valueOf(it)
            } catch (e: IllegalArgumentException) {
                product.category
            }
        } ?: product.category
        product.price = dto.price ?: product.price
        product.description = dto.description ?: product.description

        return productRepository.save(product)
    }

    override fun deleteProduct(productId: Long) {
        return productRepository.deleteById(productId)
    }
}