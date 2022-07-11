package com.example.ecommerce.model.remote.data.productDetail

data class ProductDetailResponse(
    val message: String,
    val product: Product,
    val status: Int
)