package com.example.ecommerce.model.remote.data.placeOrderRequest

data class Item(
    val product_id: Int,
    val quantity: Int,
    val unit_price: Int
)