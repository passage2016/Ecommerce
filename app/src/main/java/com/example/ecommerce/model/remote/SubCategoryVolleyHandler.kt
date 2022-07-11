package com.example.ecommerce.model.remote

import android.content.Context
import android.content.Intent
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ecommerce.model.remote.data.category.Category
import com.example.ecommerce.model.remote.data.category.CategoryResponse
import com.example.ecommerce.model.remote.data.subCategory.SubCategoryResponse
import com.google.gson.Gson
import com.learning.mvpregistrationapp.model.remote.Constants.BASE_URL
import com.learning.mvpregistrationapp.model.remote.Constants.CATEGORY_END_POINT
import com.learning.mvpregistrationapp.model.remote.Constants.LOGIN_END_POINT
import com.learning.mvpregistrationapp.model.remote.Constants.REGISTRATION_END_POINT
import com.learning.mvpregistrationapp.model.remote.Constants.SUB_CATEGORY_END_POINT
import com.learning.mvpregistrationapp.model.remote.OperationalCallback
import org.json.JSONObject

class SubCategoryVolleyHandler(private val context: Context) {

    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun getSubCategoryFromApi(categoryId: String, callback: OperationalCallback): String {
        val url = BASE_URL + SUB_CATEGORY_END_POINT + "category_id=$categoryId"
        Log.e("url", "${url}")
        var message: String? = null


        val request = object: StringRequest(Request.Method.GET, url,
            Response.Listener {
                val gson = Gson()
                val subCategoryResponse = gson.fromJson(it.toString(), SubCategoryResponse::class.java)
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