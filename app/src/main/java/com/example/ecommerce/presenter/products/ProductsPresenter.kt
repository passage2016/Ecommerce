package com.example.ecommerce.presenter.products

import com.example.ecommerce.model.remote.ProductsVolleyHandler
import com.example.ecommerce.model.remote.data.products.ProductsResponse
import com.learning.mvpregistrationapp.model.remote.OperationalCallback

class ProductsPresenter(
    private val volleyHandler: ProductsVolleyHandler,
    private val productsView: ProductsMVP.ProductsView
) : ProductsMVP.ProductsPresenter {

    override fun getProducts(subCategoryId: String): String {
        productsView.onLoad(true)
        val message = volleyHandler.getProductsFromApi(subCategoryId,
            object : OperationalCallback {
                override fun onSuccess(data: Any) {
                    productsView.onLoad(false)
                    productsView.setResult(data as ProductsResponse)
                }

                override fun onFailure(message: String) {
                    productsView.onLoad(false)
                    productsView.setResult(null)
                }
            })
        return message
    }
}