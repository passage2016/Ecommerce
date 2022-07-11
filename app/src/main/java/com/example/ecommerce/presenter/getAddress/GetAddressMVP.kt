package com.learning.mvpregistrationapp.presenter.category

import com.example.ecommerce.model.remote.data.address.AddressResponse
import com.example.ecommerce.model.remote.data.category.Category
import com.example.ecommerce.model.remote.data.category.CategoryResponse


interface GetAddressMVP {
    interface GetAddressPresenter {
        fun getAddress(userId: String): String
    }

    interface GetAddressView {
        fun setResult(addressResponse: AddressResponse?)
        fun onLoad(isLoading: Boolean)
    }
}