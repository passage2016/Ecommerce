package com.learning.mvpregistrationapp.presenter.category

import com.example.ecommerce.model.remote.AddressVolleyHandler
import com.example.ecommerce.model.remote.CategoryVolleyHandler
import com.example.ecommerce.model.remote.data.category.CategoryResponse
import com.learning.mvpregistrationapp.model.remote.OperationalCallback

class AddAddressPresenter(
    private val volleyHandler: AddressVolleyHandler,
    private val loginView: AddAddressMVP.AddAddressView
) : AddAddressMVP.AddAddressPresenter {

    override fun addAddress(userId: String, title: String, address: String): String {
        loginView.onLoad(true)
        val message = volleyHandler.addAddressToAPI(userId, title, address,
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