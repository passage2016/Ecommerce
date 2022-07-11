package com.learning.mvpregistrationapp.presenter.category

import com.example.ecommerce.model.remote.data.category.Category
import com.example.ecommerce.model.remote.data.category.CategoryResponse
import com.example.ecommerce.model.remote.data.placeOrderRequest.PlaceOrderRequest


interface PlaceOrderMVP {
    interface PlaceOrderPresenter {
        fun placeOrder(placeOrderRequest: PlaceOrderRequest): String
    }

    interface PlaceOrderView {
        fun setResult(message: String)
        fun onLoad(isLoading: Boolean)
    }
}