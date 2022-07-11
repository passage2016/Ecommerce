package com.example.ecommerce.presenter.getAddress

import com.example.ecommerce.model.remote.data.address.AddressResponse


interface GetAddressMVP {
    interface GetAddressPresenter {
        fun getAddress(userId: String): String
    }

    interface GetAddressView {
        fun setResult(addressResponse: AddressResponse?)
        fun onLoad(isLoading: Boolean)
    }
}