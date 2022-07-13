package com.example.ecommerce.presenter.logout

import com.example.ecommerce.model.remote.UserVolleyHandler
import com.example.ecommerce.model.remote.data.user.User
import com.learning.mvpregistrationapp.model.remote.OperationalCallback

class LogoutPresenter(
    private val volleyHandler: UserVolleyHandler,
    private val loginView: LogoutMVP.LogoutView
) : LogoutMVP.LogoutPresenter {

    override fun logoutUser(email: String): String {
        loginView.onLoad(true)
        val message = volleyHandler.logoutUser(email,
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