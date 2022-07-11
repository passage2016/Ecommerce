package com.example.ecommerce.view.address

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.model.remote.AddressVolleyHandler
import com.example.ecommerce.model.remote.data.address.AddressResponse
import com.example.ecommerce.model.remote.data.address.Addresse
import com.example.ecommerce.presenter.getAddress.GetAddressMVP
import com.example.ecommerce.presenter.getAddress.GetAddressPresenter
import com.example.ecommerce.view.LoginActivity.Companion.ACCOUNT_INFO_FILE_NAME
import com.example.ecommerce.view.LoginActivity.Companion.USER_ID

class AddressFragment : Fragment(), GetAddressMVP.GetAddressView {

    private lateinit var presenter: GetAddressPresenter
    lateinit var adapter: AddresseAdapter
    lateinit var categoryList: ArrayList<Addresse>
    lateinit var currentView: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        presenter = GetAddressPresenter(AddressVolleyHandler(view.context), this)
        sharedPreferences =
            this.requireActivity().getSharedPreferences(ACCOUNT_INFO_FILE_NAME, MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val userId = sharedPreferences.getString(USER_ID, "-1")
        val btnAddressAdd = view.findViewById<Button>(R.id.btn_address_add)
        btnAddressAdd.setOnClickListener {
            findNavController().navigate(R.id.add_address_action, null)
        }

        userId?.let {
            presenter.getAddress(userId)
        }


    }


    override fun setResult(addressResponse: AddressResponse?) {
        addressResponse?.let {
            categoryList = addressResponse.addresses
            currentView?.let {
                adapter = AddresseAdapter(currentView.context, categoryList)
                currentView.findViewById<RecyclerView>(R.id.rv_address).layoutManager =
                    LinearLayoutManager(currentView.context)
                currentView.findViewById<RecyclerView>(R.id.rv_address).adapter = adapter
            }
        }


    }

    override fun onLoad(isLoading: Boolean) {
    }
}