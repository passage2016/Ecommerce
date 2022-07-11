package com.example.ecommerce.view.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.model.local.CartDao
import com.example.ecommerce.model.local.CartItem
import com.example.ecommerce.model.remote.CategoryVolleyHandler
import com.example.ecommerce.model.remote.data.category.Category
import com.example.ecommerce.view.home.ShopCategoryAdapter
import com.example.ecommerce.view.home.SubCategoryFragment
import com.learning.mvpregistrationapp.presenter.category.CategoryPresenter

class CartFragment: Fragment() {
    lateinit var adapter: CartItemAdapter
    lateinit var cartItemList: ArrayList<CartItem>
    lateinit var currentView:View
    private lateinit var cartDao: CartDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        cartDao = CartDao(view.context)
        cartItemList = cartDao.getAllCartItem()
        var totalAmount: Double = 0.0
        for (i in 0 until cartItemList.size){
            val cartItem = cartItemList.get(i)
            totalAmount = totalAmount + cartItem.price * cartItem.count
        }
        view.findViewById<TextView>(R.id.tv_cart_fragment_total_amount).text = totalAmount.toString()
        adapter = CartItemAdapter(view.context, cartItemList)
        currentView.findViewById<RecyclerView>(R.id.rv_cart_item).layoutManager = LinearLayoutManager(view.context)
        currentView.findViewById<RecyclerView>(R.id.rv_cart_item).adapter = adapter

        val btnCartPlaceOrder: Button = view.findViewById(R.id.btn_cart_place_order)
        btnCartPlaceOrder.setOnClickListener {
            val action = CartFragmentDirections.placeOrderAction()
            currentView.findNavController().navigate(action)
        }

    }
}