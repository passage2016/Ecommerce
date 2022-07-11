package com.example.ecommerce.presenter.register

import com.example.ecommerce.model.remote.data.user.User

interface RegistrationMVP {

    interface RegistrationPresenter {
        fun registerUser(user: User): String
    }

    interface RegistrationView {
        fun setResult(message: String)
        fun onLoad(isLoading: Boolean)
        fun setLogin(user: User)
    }
}