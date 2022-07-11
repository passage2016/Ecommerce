package com.example.ecommerce.presenter.placeOrder

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