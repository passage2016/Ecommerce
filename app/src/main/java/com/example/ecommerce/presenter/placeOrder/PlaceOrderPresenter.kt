package com.example.ecommerce.presenter.placeOrder

import com.example.ecommerce.model.remote.OrderVolleyHandler
import com.example.ecommerce.model.remote.data.placeOrderRequest.PlaceOrderRequest
import com.learning.mvpregistrationapp.model.remote.OperationalCallback

class PlaceOrderPresenter(
    private val volleyHandler: OrderVolleyHandler,
    private val loginView: PlaceOrderMVP.PlaceOrderView
) : PlaceOrderMVP.PlaceOrderPresenter {

    override fun placeOrder(placeOrderRequest: PlaceOrderRequest): String {
        loginView.onLoad(true)
        val message = volleyHandler.placeOrderToAPI(placeOrderRequest,
            object : OperationalCallback {
                override fun onSuccess(data: Any) {
                    loginView.onLoad(false)
                    loginView.setResult(data as String)
                }

                override fun onFailure(message: String) {
                    loginView.onLoad(false)
                    loginView.setResult(message)
                }
            })
        return message
    }
}