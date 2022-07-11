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
import com.example.ecommerce.model.remote.data.address.AddressResponse
import com.example.ecommerce.model.remote.data.subCategory.SubCategoryResponse
import com.example.ecommerce.model.remote.data.user.User
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.learning.mvpregistrationapp.model.remote.Constants
import com.learning.mvpregistrationapp.model.remote.Constants.ADD_ADDRESS_END_POINT
import com.learning.mvpregistrationapp.model.remote.Constants.BASE_URL
import com.learning.mvpregistrationapp.model.remote.Constants.GET_ADDRESS_END_POINT
import com.learning.mvpregistrationapp.model.remote.Constants.LOGIN_END_POINT
import com.learning.mvpregistrationapp.model.remote.Constants.REGISTRATION_END_POINT
import com.learning.mvpregistrationapp.model.remote.OperationalCallback
import org.json.JSONObject

class AddressVolleyHandler(private val context: Context) {

    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun addAddressToAPI(userId: String, title: String, address: String, callback: OperationalCallback): String {
        val url = BASE_URL + ADD_ADDRESS_END_POINT
        val data = JSONObject()
        var message: String? = null

        data.put("user_id", userId)
        data.put("title", title)
        data.put("address", address)

        val request = JsonObjectRequest(Request.Method.POST, url, data,
            { response: JSONObject ->
                message = response.getString("message")
                Log.i("tag", message.toString())
                val status = response.getInt("status")
                Log.e("tag", "message is $message")
                if(status == 0){
                    callback.onSuccess(message.toString())
                } else {
                    callback.onFailure(message.toString())
                }

            }, { error: VolleyError ->
                error.printStackTrace()
                Log.i("tag", "${error.printStackTrace()}")
                callback.onFailure(message.toString())
            })
        requestQueue.add(request)
        return message.toString()
    }

    fun getAddressFromApi(userId: String, callback: OperationalCallback): String {
        val url = BASE_URL + GET_ADDRESS_END_POINT + userId
        Log.e("url", "${url}")
        var message: String? = null


        val request = object: StringRequest(Request.Method.GET, url,
            Response.Listener {
                val gson = Gson()
                val subCategoryResponse = gson.fromJson(it.toString(), AddressResponse::class.java)
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