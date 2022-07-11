package com.example.ecommerce.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerce.R
import com.example.ecommerce.model.remote.UserVolleyHandler
import com.example.ecommerce.model.remote.data.user.User
import com.example.ecommerce.presenter.register.RegistrationMVP
import com.example.ecommerce.presenter.register.RegistrationPresenter

class RegisterActivity : AppCompatActivity(), RegistrationMVP.RegistrationView {
    lateinit var presenter: RegistrationPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val btRegister: Button = findViewById(R.id.btn_register)
        val etRegisterEmail: EditText = findViewById(R.id.et_register_email)
        val etRegisterNAME: EditText = findViewById(R.id.et_register_name)
        val etRegisterPHONE: EditText = findViewById(R.id.et_register_phone)
        val etRegisterPassword: EditText = findViewById(R.id.et_register_password)
        val tvRegisterLogin: TextView = findViewById(R.id.tv_register_login)

        presenter = RegistrationPresenter(UserVolleyHandler(this), this)
        btRegister.setOnClickListener {
            val name = etRegisterNAME.text.toString()
            val phone = etRegisterPHONE.text.toString()
            val email = etRegisterEmail.text.toString()
            val password = etRegisterPassword.text.toString()
            val user = User(null, name, phone, email, password)
            presenter.registerUser(user)
        }

        tvRegisterLogin.setOnClickListener {
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onLoad(isLoading: Boolean) {
    }

    override fun setResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setLogin(user: User) {
        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}