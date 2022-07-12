package com.example.ecommerce.presenter.products

import com.example.ecommerce.model.remote.data.products.ProductsResponse


interface ProductsMVP {
    interface ProductsPresenter {
        fun getProducts(subCategoryId: String): String
        fun searchProducts(query: String): String
    }

    interface ProductsView {
        fun setResult(productsResponse: ProductsResponse?)
        fun onLoad(isLoading: Boolean)
    }
}