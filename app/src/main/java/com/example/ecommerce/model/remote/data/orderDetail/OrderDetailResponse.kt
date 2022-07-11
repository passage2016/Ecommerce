package com.example.ecommerce.model.remote.data.orderDetail

data class OrderDetailResponse(
    val message: String,
    val order: Order,
    val status: Int
)