package com.example.ecommerce.presenter.subCategory

import com.example.ecommerce.model.remote.data.subCategory.SubCategoryResponse


interface SubCategoryMVP {
    interface SubCategoryPresenter {
        fun getSubCategory(categoryId: String): String
    }

    interface SubCategoryView {
        fun setResult(subCategoryResponse: SubCategoryResponse?)
        fun onLoad(isLoading: Boolean)
    }
}