package com.example.ecommerce.presenter.productDetail

import com.example.ecommerce.model.remote.ProductDetailVolleyHandler
import com.example.ecommerce.model.remote.data.productDetail.ProductDetailResponse
import com.learning.mvpregistrationapp.model.remote.OperationalCallback

class ProductDetailPresenter(
    private val volleyHandler: ProductDetailVolleyHandler,
    private val loginView: ProductDetailMVP.ProductDetailView
) : ProductDetailMVP.ProductDetailPresenter {

    override fun getProductDetail(productId: String): String {
        loginView.onLoad(true)
        val message = volleyHandler.getProductDetailFromApi(productId,
            object : OperationalCallback {
                override fun onSuccess(data: Any) {
                    loginView.onLoad(false)
                    loginView.setResult(data as ProductDetailResponse)
                }

                override fun onFailure(message: String) {
                    loginView.onLoad(false)
                    loginView.setResult(null)
                }
            })
        return message
    }
}