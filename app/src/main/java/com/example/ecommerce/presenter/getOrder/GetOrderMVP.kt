package com.example.ecommerce.presenter.getOrder

import com.example.ecommerce.model.remote.data.order.OrderResponse


interface GetOrderMVP {
    interface GetOrderPresenter {
        fun getOrders(userId: String): String
    }

    interface GetOrderView {
        fun setResult(orderResponse: OrderResponse?)
        fun onLoad(isLoading: Boolean)
    }
}