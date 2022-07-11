package com.example.ecommerce.model.remote.data.order

data class OrderResponse(
    val message: String,
    val orders: ArrayList<Order>,
    val status: Int
)