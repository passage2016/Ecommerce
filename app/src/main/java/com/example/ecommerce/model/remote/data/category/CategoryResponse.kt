package com.example.ecommerce.model.remote.data.category

data class CategoryResponse(
    val categories: ArrayList<Category>,
    val message: String,
    val status: Int
)