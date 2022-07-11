package com.learning.mvpregistrationapp.presenter.category

import com.example.ecommerce.model.remote.data.address.AddressResponse
import com.example.ecommerce.model.remote.data.category.Category
import com.example.ecommerce.model.remote.data.category.CategoryResponse
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