package com.example.ecommerce.presenter.getOrderDetail

import com.example.ecommerce.model.remote.OrderVolleyHandler
import com.example.ecommerce.model.remote.data.orderDetail.OrderDetailResponse
import com.learning.mvpregistrationapp.model.remote.OperationalCallback

class GetOrderDetailPresenter(
    private val volleyHandler: OrderVolleyHandler,
    private val loginView: GetOrderDetailMVP.GetOrderDetailView
) : GetOrderDetailMVP.GetOrderDetailPresenter {

    override fun getOrderDetail(orderId: String): String {
        loginView.onLoad(true)
        val message = volleyHandler.getOrderDetailFromApi(orderId,
            object : OperationalCallback {
                override fun onSuccess(data: Any) {
                    loginView.onLoad(false)
                    loginView.setResult(data as OrderDetailResponse)
                }

                override fun onFailure(message: String) {
                    loginView.onLoad(false)
                    loginView.setResult(null)
                }
            })
        return message
    }
}