package com.learning.mvpregistrationapp.presenter.category

import com.example.ecommerce.model.remote.CategoryVolleyHandler
import com.example.ecommerce.model.remote.data.category.CategoryResponse
import com.learning.mvpregistrationapp.model.remote.OperationalCallback

class CategoryPresenter(
    private val volleyHandler: CategoryVolleyHandler,
    private val loginView: CategoryMVP.CategoryView
) : CategoryMVP.CategoryPresenter {

    override fun getCategory(): String {
        loginView.onLoad(true)
        val message = volleyHandler.getCategoryFromApi(
            object : OperationalCallback {
                override fun onSuccess(data: Any) {
                    loginView.onLoad(false)
                    loginView.setResult(data as CategoryResponse)
                }

                override fun onFailure(message: String) {
                    loginView.onLoad(false)
                    loginView.setResult(null)
                }
            })
        return message
    }
}