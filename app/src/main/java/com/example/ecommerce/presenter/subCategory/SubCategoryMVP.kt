package com.learning.mvpregistrationapp.presenter.category

import com.example.ecommerce.model.remote.data.category.Category
import com.example.ecommerce.model.remote.data.category.CategoryResponse
import com.example.ecommerce.model.remote.data.subCategory.SubCategoryResponse


interface SubCategoryMVP {
    interface SubCategoryPresenter {
        fun getSubCategory(categoryId:String): String
    }

    interface SubCategoryView {
        fun setResult(subCategoryResponse: SubCategoryResponse?)
        fun onLoad(isLoading: Boolean)
    }
}