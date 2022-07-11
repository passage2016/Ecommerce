package com.learning.mvpregistrationapp.presenter.category

import com.example.ecommerce.model.remote.data.category.Category
import com.example.ecommerce.model.remote.data.category.CategoryResponse
import com.example.ecommerce.model.remote.data.placeOrderRequest.PlaceOrderRequest


interface AddAddressMVP {
    interface AddAddressPresenter {
        fun addAddress(userId: String, title: String, address: String): String
    }

    interface AddAddressView {
        fun setResult(message: String)
        fun onLoad(isLoading: Boolean)
    }
}