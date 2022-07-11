package com.learning.mvpregistrationapp.model.remote

interface OperationalCallback {
    fun onSuccess(data: Any)
    fun onFailure(message: String)
}