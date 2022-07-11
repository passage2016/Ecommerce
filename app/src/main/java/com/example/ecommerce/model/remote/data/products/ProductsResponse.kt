package com.example.ecommerce.model.remote.data.products

data class ProductsResponse(
    val message: String,
    val products: ArrayList<Product>,
    val status: Int
)