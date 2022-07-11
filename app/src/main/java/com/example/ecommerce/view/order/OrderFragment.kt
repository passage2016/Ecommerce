package com.example.ecommerce.view.order

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.model.local.CartDao
import com.example.ecommerce.model.local.CartItem
import com.example.ecommerce.model.remote.AddressVolleyHandler
import com.example.ecommerce.model.remote.OrderVolleyHandler
import com.example.ecommerce.model.remote.data.order.Order
import com.example.ecommerce.model.remote.data.order.OrderResponse
import com.example.ecommerce.view.LoginActivity
import com.example.ecommerce.view.cart.CartFragmentDirections
import com.example.ecommerce.view.cart.CartItemAdapter
import com.learning.mvpregistrationapp.presenter.category.AddAddressPresenter
import com.learning.mvpregistrationapp.presenter.category.GetOrderMVP
import com.learning.mvpregistrationapp.presenter.category.GetOrderPresenter

class OrderFragment: Fragment(), GetOrderMVP.GetOrderView {
    lateinit var adapter: OrderAdapter
    lateinit var orderList: ArrayList<Order>
    lateinit var currentView: View
    private lateinit var presenter: GetOrderPresenter
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        presenter = GetOrderPresenter(OrderVolleyHandler(view.context), this)
        sharedPreferences = this.requireActivity().getSharedPreferences(LoginActivity.ACCOUNT_INFO_FILE_NAME, AppCompatActivity.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val userId = sharedPreferences.getString(LoginActivity.USER_ID, "-1")
        userId?.let {
            presenter.getOrders(userId)
        }

    }

    override fun setResult(orderResponse: OrderResponse?) {
        orderResponse?.let {
            orderList = orderResponse.orders
            adapter = OrderAdapter(currentView.context, orderList)
            currentView.findViewById<RecyclerView>(R.id.rv_choose_order).layoutManager = LinearLayoutManager(currentView.context)
            currentView.findViewById<RecyclerView>(R.id.rv_choose_order).adapter = adapter
        }

    }

    override fun onLoad(isLoading: Boolean) {
    }
}