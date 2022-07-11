package com.example.ecommerce.view.address

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ecommerce.R
import com.example.ecommerce.model.remote.AddressVolleyHandler
import com.example.ecommerce.presenter.addAddress.AddAddressMVP
import com.example.ecommerce.presenter.addAddress.AddAddressPresenter
import com.example.ecommerce.view.LoginActivity.Companion.ACCOUNT_INFO_FILE_NAME
import com.example.ecommerce.view.LoginActivity.Companion.USER_ID

class AddAddressFragment : Fragment(), AddAddressMVP.AddAddressView {
    private lateinit var presenter: AddAddressPresenter
    lateinit var currentView: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_add_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        val btnAddAddress: Button = view.findViewById(R.id.btn_add_address)
        val etAddAddressTitle: EditText = view.findViewById(R.id.et_add_address_title)
        val etAddAddressAddress: EditText = view.findViewById(R.id.et_add_address_address)

        presenter = AddAddressPresenter(AddressVolleyHandler(view.context), this)
        btnAddAddress.setOnClickListener {
            val title = etAddAddressTitle.text.toString()
            val address = etAddAddressAddress.text.toString()
            sharedPreferences = this.requireActivity()
                .getSharedPreferences(ACCOUNT_INFO_FILE_NAME, AppCompatActivity.MODE_PRIVATE)
            editor = sharedPreferences.edit()
            val userId = sharedPreferences.getString(USER_ID, "-1")
            userId?.let {
                presenter.addAddress(userId, title, address)
            }

        }

    }

    override fun setResult(message: String) {
        Toast.makeText(currentView.context, message, Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.address_dest, null)
    }

    override fun onLoad(isLoading: Boolean) {
    }
}