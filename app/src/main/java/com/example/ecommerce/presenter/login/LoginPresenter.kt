package com.example.ecommerce.presenter.login

import com.example.ecommerce.model.remote.UserVolleyHandler
import com.example.ecommerce.model.remote.data.user.User
import com.learning.mvpregistrationapp.model.remote.OperationalCallback

class LoginPresenter(
    private val volleyHandler: UserVolleyHandler,
    private val loginView: LoginMVP.LoginView
) : LoginMVP.LoginPresenter {

    override fun loginUser(user: User): String {
        loginView.onLoad(true)
        val message = volleyHandler.loginUser(user,
            object : OperationalCallback {
                override fun onSuccess(data: Any) {
                    loginView.onLoad(false)
                    loginView.setResult(data as String)
                    loginView.setLogin(user)
                }

                override fun onFailure(message: String) {
                    loginView.onLoad(false)
                    loginView.setResult(message)
                }
            })
        return message
    }
}