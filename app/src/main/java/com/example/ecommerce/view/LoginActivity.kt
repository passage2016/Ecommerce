package com.example.ecommerce.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.ecommerce.R
import com.example.ecommerce.model.remote.UserVolleyHandler
import com.example.ecommerce.model.remote.data.user.User
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.learning.mvpregistrationapp.presenter.login.LoginMVP
import com.learning.mvpregistrationapp.presenter.login.LoginPresenter
import org.json.JSONObject

class LoginActivity: AppCompatActivity(), LoginMVP.LoginView {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnLogin: Button = findViewById(R.id.btn_login)
        val etLoginEmail: EditText =  findViewById(R.id.et_login_email)
        val etLoginPassword: EditText = findViewById(R.id.et_login_password)
        val tvLoginRegister: TextView = findViewById(R.id.tv_login_register)

        presenter = LoginPresenter(UserVolleyHandler(this), this)
        btnLogin.setOnClickListener {
            val email = etLoginEmail.text.toString()
            val password = etLoginPassword.text.toString()
            val user = User(null, null, null, email, password)
            presenter.loginUser(user)
        }

        tvLoginRegister.setOnClickListener {
            val intent: Intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onLoad(isLoading: Boolean) {
    }

    override fun setResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setLogin(user: User) {
        val intent: Intent = Intent(this, MainActivity::class.java)
        sharedPreferences = getSharedPreferences(ACCOUNT_INFO_FILE_NAME, MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.putString(USER_ID, user.userID)
        editor.putString(EMAIL, user.emailId)
        editor.putString(PHONE, user.mobileNo)
        editor.putString(NAME, user.fullName)
        editor.putString(PASSWORD, user.password)
        editor.apply()
        startActivity(intent)
    }

    companion object {
        const val ACCOUNT_INFO_FILE_NAME = "Account Info"
        const val USER_ID = "userID"
        const val EMAIL = "email"
        const val PHONE = "phone"
        const val NAME = "name"
        const val PASSWORD = "password"
    }
}