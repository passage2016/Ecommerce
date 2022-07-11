package com.example.ecommerce.model.remote.data.productDetail

data class Product(
    val average_rating: String,
    val category_id: String,
    val description: String,
    val images: ArrayList<Image>,
    val is_active: String,
    val price: String,
    val product_id: String,
    val product_image_url: String,
    val product_name: String,
    val reviews: ArrayList<Any>,
    val specifications: ArrayList<Specification>,
    val sub_category_id: String
)