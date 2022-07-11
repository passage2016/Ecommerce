package com.learning.mvpregistrationapp.presenter.category

import com.example.ecommerce.model.remote.AddressVolleyHandler
import com.example.ecommerce.model.remote.CategoryVolleyHandler
import com.example.ecommerce.model.remote.OrderVolleyHandler
import com.example.ecommerce.model.remote.data.address.AddressResponse
import com.example.ecommerce.model.remote.data.category.CategoryResponse
import com.example.ecommerce.model.remote.data.order.OrderResponse
import com.learning.mvpregistrationapp.model.remote.OperationalCallback

class GetOrderPresenter(
    private val volleyHandler: OrderVolleyHandler,
    private val loginView: GetOrderMVP.GetOrderView
) : GetOrderMVP.GetOrderPresenter {

    override fun getOrders(userId: String): String {
        loginView.onLoad(true)
        val message = volleyHandler.getOrdersFromApi(userId,
            object : OperationalCallback {
                override fun onSuccess(data: Any) {
                    loginView.onLoad(false)
                    loginView.setResult(data as OrderResponse)
                }

                override fun onFailure(message: String) {
                    loginView.onLoad(false)
                    loginView.setResult(null)
                }
            })
        return message
    }
}