package com.example.ecommerce.view.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.ecommerce.R
import com.example.ecommerce.model.local.CartDao
import com.example.ecommerce.model.local.CartItem

class CheckoutCartItemFragment : Fragment() {
    lateinit var adapter: CheckoutCartItemAdapter
    lateinit var cartItemList: ArrayList<CartItem>
    lateinit var currentView: View
    private lateinit var cartDao: CartDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_checkout_cart_item, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        communicator = context as Communicator
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        cartDao = CartDao(view.context)
        cartItemList = cartDao.getAllCartItem()
        var totalAmount: Double = 0.0
        for (i in 0 until cartItemList.size) {
            val cartItem = cartItemList.get(i)
            totalAmount = totalAmount + cartItem.price * cartItem.count
        }
        view.findViewById<TextView>(R.id.tv_checkout_cart_fragment_total_amount).text =
            totalAmount.toString()
        adapter = CheckoutCartItemAdapter(view.context, cartItemList)
        currentView.findViewById<RecyclerView>(R.id.rv_checkout_cart_item).layoutManager =
            LinearLayoutManager(view.context)
        currentView.findViewById<RecyclerView>(R.id.rv_checkout_cart_item).adapter = adapter

        val btnCheckoutCartItemNext: Button = view.findViewById(R.id.btn_checkout_cart_item_next)
        btnCheckoutCartItemNext.setOnClickListener {
            (this.parentFragment as CheckoutFragment).nextPager()
        }

    }
}