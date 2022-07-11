package com.example.ecommerce.model.remote

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ecommerce.model.remote.data.category.CategoryResponse
import com.google.gson.Gson
import com.learning.mvpregistrationapp.model.remote.Constants.BASE_URL
import com.learning.mvpregistrationapp.model.remote.Constants.CATEGORY_END_POINT
import com.learning.mvpregistrationapp.model.remote.OperationalCallback

class CategoryVolleyHandler(private val context: Context) {

    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun getCategoryFromApi(callback: OperationalCallback): String {
        val url = BASE_URL + CATEGORY_END_POINT
        var message: String? = null


        val request = object: StringRequest(Request.Method.GET, url,
            Response.Listener {
                val gson = Gson()
                val categoryResponse = gson.fromJson(it.toString(), CategoryResponse::class.java)
                callback.onSuccess(categoryResponse)
                Log.e("tag", it.toString())
            }, {
                Log.e("tag", it.toString())
            }){
        }
        requestQueue.add(request)
        return message.toString()
    }

}