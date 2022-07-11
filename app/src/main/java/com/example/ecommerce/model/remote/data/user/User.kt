package com.example.ecommerce.model.remote.data.user

data class User(
    var userID: String?,
    var fullName: String?,
    var mobileNo: String?,
    val emailId: String,
    val password: String
)