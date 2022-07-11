package com.example.ecommerce.model.remote.data.address

data class AddressResponse(
    val addresses: ArrayList<Addresse>,
    val message: String,
    val status: Int
)