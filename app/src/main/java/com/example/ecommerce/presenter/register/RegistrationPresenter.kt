package com.example.ecommerce.presenter.register

import com.example.ecommerce.model.remote.UserVolleyHandler
import com.example.ecommerce.model.remote.data.user.User
import com.learning.mvpregistrationapp.model.remote.OperationalCallback

class RegistrationPresenter(
    private val volleyHandler: UserVolleyHandler,
    private val registrationView: RegistrationMVP.RegistrationView
) : RegistrationMVP.RegistrationPresenter {

    override fun registerUser(user: User): String {
        registrationView.onLoad(true)
        val message = volleyHandler.registerUser(user,
            object : OperationalCallback {
                override fun onSuccess(data: Any) {
                    registrationView.onLoad(false)
                    registrationView.setResult(data as String)
                    registrationView.setLogin(user)
                }

                override fun onFailure(message: String) {
                    registrationView.onLoad(false)
                    registrationView.setResult(message)
                }
            })
        return message
    }
}