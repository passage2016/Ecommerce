package com.example.ecommerce.model.local

data class CartItem(
    var cartId: Long?,
    val productName: String,
    val productId: String,
    val description: String,
    val price: Double,
    val categoryId: String,
    val subCategoryId: String,
    val productImageUrl: String,
    var count: Int
)