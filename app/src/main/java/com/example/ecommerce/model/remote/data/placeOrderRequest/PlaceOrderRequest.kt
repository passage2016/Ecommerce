package com.example.ecommerce.model.remote.data.placeOrderRequest

data class PlaceOrderRequest(
    val bill_amount: Int,
    val delivery_address: DeliveryAddress,
    val items: ArrayList<Item>,
    val payment_method: String,
    val user_id: Int
)