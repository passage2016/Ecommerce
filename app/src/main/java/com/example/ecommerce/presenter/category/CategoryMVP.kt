package com.example.ecommerce.presenter.category

import com.example.ecommerce.model.remote.data.category.CategoryResponse


interface CategoryMVP {
    interface CategoryPresenter {
        fun getCategory(): String
    }

    interface CategoryView {
        fun setResult(categoryResponse: CategoryResponse?)
        fun onLoad(isLoading: Boolean)
    }
}