package com.example.ecommerce.model.remote

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ecommerce.model.remote.data.products.ProductsResponse
import com.google.gson.Gson
import com.learning.mvpregistrationapp.model.remote.Constants.BASE_URL
import com.learning.mvpregistrationapp.model.remote.Constants.PRODUCTS_END_POINT
import com.learning.mvpregistrationapp.model.remote.OperationalCallback

class ProductsVolleyHandler(private val context: Context) {

    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun getProductsFromApi(subCategoryId: String, callback: OperationalCallback): String {
        val url = BASE_URL + PRODUCTS_END_POINT + subCategoryId
        Log.e("url", "${url}")
        var message: String? = null


        val request = object: StringRequest(Request.Method.GET, url,
            Response.Listener {
                val gson = Gson()
                val productsResponse = gson.fromJson(it.toString(), ProductsResponse::class.java)
                callback.onSuccess(productsResponse)
                Log.e("tag", it.toString())
            }, {
                Log.e("tag", it.toString())
            }){
        }
        requestQueue.add(request)
        return message.toString()
    }

}