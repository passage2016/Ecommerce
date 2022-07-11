package com.example.ecommerce.presenter.getAddress

import com.example.ecommerce.model.remote.AddressVolleyHandler
import com.example.ecommerce.model.remote.data.address.AddressResponse
import com.learning.mvpregistrationapp.model.remote.OperationalCallback

class GetAddressPresenter(
    private val volleyHandler: AddressVolleyHandler,
    private val loginView: GetAddressMVP.GetAddressView
) : GetAddressMVP.GetAddressPresenter {

    override fun getAddress(userId: String): String {
        loginView.onLoad(true)
        val message = volleyHandler.getAddressFromApi(userId,
            object : OperationalCallback {
                override fun onSuccess(data: Any) {
                    loginView.onLoad(false)
                    loginView.setResult(data as AddressResponse)
                }

                override fun onFailure(message: String) {
                    loginView.onLoad(false)
                    loginView.setResult(null)
                }
            })
        return message
    }
}