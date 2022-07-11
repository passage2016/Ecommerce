package com.example.ecommerce.view.cart

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.model.remote.AddressVolleyHandler
import com.example.ecommerce.model.remote.CategoryVolleyHandler
import com.example.ecommerce.model.remote.data.address.AddressResponse
import com.example.ecommerce.model.remote.data.address.Addresse
import com.example.ecommerce.model.remote.data.category.Category
import com.example.ecommerce.model.remote.data.category.CategoryResponse
import com.example.ecommerce.view.LoginActivity
import com.example.ecommerce.view.LoginActivity.Companion.USER_ID
import com.example.ecommerce.view.cart.CartFragmentDirections
import com.example.ecommerce.view.home.ShopCategoryAdapter
import com.example.ecommerce.view.home.SubCategoryFragment
import com.learning.mvpregistrationapp.presenter.category.CategoryPresenter
import com.learning.mvpregistrationapp.presenter.category.GetAddressMVP
import com.learning.mvpregistrationapp.presenter.category.GetAddressPresenter

class ChooseAddressFragment: Fragment(), GetAddressMVP.GetAddressView {

    private lateinit var presenter: GetAddressPresenter
    lateinit var adapter: ChooseAddresseAdapter
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
        return inflater.inflate(R.layout.fragment_choose_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        presenter = GetAddressPresenter(AddressVolleyHandler(view.context), this)
        sharedPreferences = this.requireActivity().getSharedPreferences(LoginActivity.ACCOUNT_INFO_FILE_NAME, AppCompatActivity.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val userId = sharedPreferences.getString(USER_ID, "-1")

        userId?.let {
            presenter.getAddress(userId)
        }



    }



    override fun setResult(addressResponse: AddressResponse?) {
        addressResponse?.let {
            categoryList = addressResponse.addresses
            currentView?.let {
                adapter = ChooseAddresseAdapter(currentView.context, categoryList)
                currentView.findViewById<RecyclerView>(R.id.rv_choose_address).layoutManager = LinearLayoutManager(currentView.context)
                currentView.findViewById<RecyclerView>(R.id.rv_choose_address).adapter = adapter
            }
        }


    }

    override fun onLoad(isLoading: Boolean) {
    }
}