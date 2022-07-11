package com.example.ecommerce.presenter.productDetail

import com.example.ecommerce.model.remote.data.productDetail.ProductDetailResponse


interface ProductDetailMVP {
    interface ProductDetailPresenter {
        fun getProductDetail(productId: String): String
    }

    interface ProductDetailView {
        fun setResult(productDetailResponse: ProductDetailResponse?)
        fun onLoad(isLoading: Boolean)
    }
}