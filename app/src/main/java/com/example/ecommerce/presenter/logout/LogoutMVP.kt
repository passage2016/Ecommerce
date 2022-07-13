package com.example.ecommerce.presenter.logout

import com.example.ecommerce.model.remote.data.user.User

interface LogoutMVP {
    interface LogoutPresenter {
        fun logoutUser(email: String): String
    }

    interface LogoutView {
        fun setResult(message: String)
        fun onLoad(isLoading: Boolean)
    }
}