package com.example.ecommerce.presenter.getOrderDetail

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