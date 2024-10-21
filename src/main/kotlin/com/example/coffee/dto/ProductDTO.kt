package com.example.coffee.dto

import com.example.coffee.constant.Category
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class ProductDto(
    var productId: Long? = null,

    @NotBlank(message = "상품명은 필수항목입니다.")
    var productName: String? = null,

    @NotBlank(message = "카테고리는 필수 항목입니다.")
    var category: String? = null,

    @NotNull(message = "가격은 필수항목입니다.")
    var price: Long? = null,

    var description: String? = null,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null
)