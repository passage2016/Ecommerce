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
import com.example.ecommerce.model.remote.data.order.OrderResponse
import com.example.ecommerce.model.remote.data.orderDetail.OrderDetailResponse
import com.example.ecommerce.model.remote.data.placeOrderRequest.PlaceOrderRequest
import com.example.ecommerce.model.remote.data.subCategory.SubCategoryResponse
import com.example.ecommerce.model.remote.data.user.User
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.learning.mvpregistrationapp.model.remote.Constants
import com.learning.mvpregistrationapp.model.remote.Constants.ADD_ADDRESS_END_POINT
import com.learning.mvpregistrationapp.model.remote.Constants.BASE_URL
import com.learning.mvpregistrationapp.model.remote.Constants.GET_ADDRESS_END_POINT
import com.learning.mvpregistrationapp.model.remote.Constants.GET_ORDERS_END_POINT
import com.learning.mvpregistrationapp.model.remote.Constants.GET_ORDER_DETAIL_END_POINT
import com.learning.mvpregistrationapp.model.remote.Constants.LOGIN_END_POINT
import com.learning.mvpregistrationapp.model.remote.Constants.PLACE_ORDER_END_POINT
import com.learning.mvpregistrationapp.model.remote.Constants.REGISTRATION_END_POINT
import com.learning.mvpregistrationapp.model.remote.OperationalCallback
import org.json.JSONObject

class OrderVolleyHandler(private val context: Context) {

    private var requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun placeOrderToAPI(placeOrderRequest: PlaceOrderRequest, callback: OperationalCallback): String {
        val url = BASE_URL + PLACE_ORDER_END_POINT
        var message: String? = null
        val gson = Gson()
        val data = JSONObject(gson.toJson(placeOrderRequest))
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

    fun getOrdersFromApi(userId: String, callback: OperationalCallback): String {
        val url = BASE_URL + GET_ORDERS_END_POINT + userId
        Log.e("url", "${url}")
        var message: String? = null


        val request = object: StringRequest(Request.Method.GET, url,
            Response.Listener {
                val gson = Gson()
                val orderResponse = gson.fromJson(it.toString(), OrderResponse::class.java)
                callback.onSuccess(orderResponse)
                Log.e("tag", it.toString())
            }, {
                Log.e("tag", it.toString())
            }){
        }
        requestQueue.add(request)
        return message.toString()
    }

    fun getOrderDetailFromApi(orderId: String, callback: OperationalCallback): String {
        val url = BASE_URL + GET_ORDER_DETAIL_END_POINT + "order_id=" +orderId
        Log.e("url", "${url}")
        var message: String? = null


        val request = object: StringRequest(Request.Method.GET, url,
            Response.Listener {
                val gson = Gson()
                val orderDetailResponse = gson.fromJson(it.toString(), OrderDetailResponse::class.java)
                callback.onSuccess(orderDetailResponse)
                Log.e("tag", it.toString())
            }, {
                Log.e("tag", it.toString())
            }){
        }
        requestQueue.add(request)
        return message.toString()
    }
}