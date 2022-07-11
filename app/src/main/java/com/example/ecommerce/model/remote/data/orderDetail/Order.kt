package com.example.ecommerce.model.remote.data.orderDetail

data class Order(
    val address: String,
    val address_title: String,
    val bill_amount: String,
    val items: ArrayList<Any>,
    val order_date: String,
    val order_id: String,
    val order_status: String,
    val payment_method: String
)