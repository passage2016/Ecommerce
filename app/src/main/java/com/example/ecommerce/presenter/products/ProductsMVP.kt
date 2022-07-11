package com.learning.mvpregistrationapp.presenter.category

import com.example.ecommerce.model.remote.data.category.Category
import com.example.ecommerce.model.remote.data.category.CategoryResponse
import com.example.ecommerce.model.remote.data.products.ProductsResponse
import com.example.ecommerce.model.remote.data.subCategory.SubCategoryResponse


interface ProductsMVP {
    interface ProductsPresenter {
        fun getProducts(subCategoryId:String): String
    }

    interface ProductsView {
        fun setResult(productsResponse: ProductsResponse?)
        fun onLoad(isLoading: Boolean)
    }
}