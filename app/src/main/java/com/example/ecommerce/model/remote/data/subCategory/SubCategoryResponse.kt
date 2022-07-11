package com.example.ecommerce.model.remote.data.subCategory

data class SubCategoryResponse(
    val message: String,
    val status: Int,
    val subcategories: ArrayList<Subcategory>
)