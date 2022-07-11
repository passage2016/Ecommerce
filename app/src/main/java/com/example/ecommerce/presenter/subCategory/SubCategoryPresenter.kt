package com.example.ecommerce.presenter.subCategory

import com.example.ecommerce.model.remote.SubCategoryVolleyHandler
import com.example.ecommerce.model.remote.data.subCategory.SubCategoryResponse
import com.learning.mvpregistrationapp.model.remote.OperationalCallback

class SubCategoryPresenter(
    private val volleyHandler: SubCategoryVolleyHandler,
    private val loginView: SubCategoryMVP.SubCategoryView
) : SubCategoryMVP.SubCategoryPresenter {

    override fun getSubCategory(categoryId: String): String {
        loginView.onLoad(true)
        val message = volleyHandler.getSubCategoryFromApi(categoryId,
            object : OperationalCallback {
                override fun onSuccess(data: Any) {
                    loginView.onLoad(false)
                    loginView.setResult(data as SubCategoryResponse)
                }

                override fun onFailure(message: String) {
                    loginView.onLoad(false)
                    loginView.setResult(null)
                }
            })
        return message
    }
}