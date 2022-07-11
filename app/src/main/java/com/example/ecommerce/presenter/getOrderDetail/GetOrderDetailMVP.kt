package com.learning.mvpregistrationapp.presenter.category

import com.example.ecommerce.model.remote.data.address.AddressResponse
import com.example.ecommerce.model.remote.data.category.Category
import com.example.ecommerce.model.remote.data.category.CategoryResponse
import com.example.ecommerce.model.remote.data.order.OrderResponse
import com.example.ecommerce.model.remote.data.orderDetail.OrderDetailResponse


interface GetOrderDetailMVP {
    interface GetOrderDetailPresenter {
        fun getOrderDetail(orderId: String): String
    }

    interface GetOrderDetailView {
        fun setResult(orderDetailResponse: OrderDetailResponse?)
        fun onLoad(isLoading: Boolean)
    }
}