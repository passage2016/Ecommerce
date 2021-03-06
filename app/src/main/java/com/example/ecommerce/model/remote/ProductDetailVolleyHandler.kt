package com.example.ecommerce.model.remote

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ecommerce.model.remote.data.productDetail.ProductDetailResponse
import com.google.gson.Gson
import com.learning.mvpregistrationapp.model.remote.Constants.BASE_URL
import com.learning.mvpregistrationapp.model.remote.Constants.PRODUCT_DETAIL_END_POINT
import com.learning.mvpregistrationapp.model.remote.OperationalCallback

class ProductDetailVolleyHandler(private val context: Context) {

    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun getProductDetailFromApi(productId: String, callback: OperationalCallback): String {
        val url = BASE_URL + PRODUCT_DETAIL_END_POINT + productId
        Log.e("url", "${url}")
        var message: String? = null


        val request = object: StringRequest(Request.Method.GET, url,
            Response.Listener {
                val gson = Gson()
                val subCategoryResponse = gson.fromJson(it.toString(), ProductDetailResponse::class.java)
                callback.onSuccess(subCategoryResponse)
                Log.e("tag", it.toString())
            }, {
                Log.e("tag", it.toString())
            }){
        }
        requestQueue.add(request)
        return message.toString()
    }

}