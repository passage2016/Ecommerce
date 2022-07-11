package com.learning.mvpregistrationapp.presenter.category

import com.example.ecommerce.model.remote.data.category.Category
import com.example.ecommerce.model.remote.data.category.CategoryResponse
import com.example.ecommerce.model.remote.data.productDetail.ProductDetailResponse
import com.example.ecommerce.model.remote.data.subCategory.SubCategoryResponse


interface ProductDetailMVP {
    interface ProductDetailPresenter {
        fun getProductDetail(productId:String): String
    }

    interface ProductDetailView {
        fun setResult(productDetailResponse: ProductDetailResponse?)
        fun onLoad(isLoading: Boolean)
    }
}