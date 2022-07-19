package com.example.ecommerce.view.cart

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.model.local.CartDao
import com.example.ecommerce.model.local.CartItem
import com.example.ecommerce.model.remote.OrderVolleyHandler
import com.example.ecommerce.model.remote.data.placeOrderRequest.DeliveryAddress
import com.example.ecommerce.model.remote.data.placeOrderRequest.Item
import com.example.ecommerce.model.remote.data.placeOrderRequest.PlaceOrderRequest
import com.example.ecommerce.presenter.placeOrder.PlaceOrderMVP
import com.example.ecommerce.presenter.placeOrder.PlaceOrderPresenter
import com.example.ecommerce.view.LoginActivity
import com.example.ecommerce.view.cart.CheckoutDeliverFragment.Companion.ADDRESS_ADDRESS
import com.example.ecommerce.view.cart.CheckoutDeliverFragment.Companion.ADDRESS_TITLE
import com.example.ecommerce.view.cart.CheckoutPaymentFragment.Companion.PAYMENT
import com.google.android.material.progressindicator.CircularProgressIndicator

class CheckoutSummaryFragment : Fragment(), PlaceOrderMVP.PlaceOrderView {
    lateinit var adapter: CheckoutCartItemAdapter
    lateinit var cartItemList: ArrayList<CartItem>
    private lateinit var presenter: PlaceOrderPresenter
    lateinit var currentView: View
    private lateinit var cartDao: CartDao
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_checkout_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        presenter = PlaceOrderPresenter(OrderVolleyHandler(view.context), this)
        cartDao = CartDao(view.context)
        cartItemList = cartDao.getAllCartItem()
        sharedPreferences = this.requireActivity().getSharedPreferences(
            LoginActivity.ACCOUNT_INFO_FILE_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()


        var totalAmount: Double = 0.0
        for (i in 0 until cartItemList.size) {
            val cartItem = cartItemList.get(i)
            totalAmount = totalAmount + cartItem.price * cartItem.count
        }
        view.findViewById<TextView>(R.id.tv_checkout_summary_total_amount).text =
            totalAmount.toString()
        adapter = CheckoutCartItemAdapter(view.context, cartItemList)
        currentView.findViewById<RecyclerView>(R.id.rv_checkout_summary_cart_item).layoutManager =
            LinearLayoutManager(view.context)
        currentView.findViewById<RecyclerView>(R.id.rv_checkout_summary_cart_item).adapter = adapter

        val payment = sharedPreferences.getString(PAYMENT, "None")
        val addressTitle = sharedPreferences.getString(ADDRESS_TITLE, "None")
        val addressAddress = sharedPreferences.getString(ADDRESS_ADDRESS, "None")

        if (payment != null) {
            view.findViewById<TextView>(R.id.tv_checkout_summary_payment_info).text = payment
        }

        if (addressAddress != null && addressTitle != null) {
            view.findViewById<TextView>(R.id.tv_checkout_summary_delivery_address_title_info).text =
                addressTitle
            view.findViewById<TextView>(R.id.tv_checkout_summary_delivery_address_address_info).text =
                addressAddress
        }


        val btnSummaryPlaceOrder: Button =
            view.findViewById(R.id.btn_checkout_summary_cart_item_place_order)
        btnSummaryPlaceOrder.setOnClickListener {

            val userId = sharedPreferences.getString(LoginActivity.USER_ID, "-1")

            userId?.let {
                val cartItemList = cartDao.getAllCartItem()
                var itemList = ArrayList<Item>()
                var totalAmount: Int = 0
                for (i in 0 until cartItemList.size) {
                    val cartItem = cartItemList.get(i)
                    totalAmount = totalAmount + (cartItem.price * cartItem.count).toInt()
                    itemList.add(
                        Item(
                            cartItem.cartId!!.toInt(),
                            cartItem.count,
                            cartItem.price.toInt()
                        )
                    )
                }


                if (payment != null && addressAddress != null && addressTitle != null) {
                    val deliveryAddress = DeliveryAddress(addressAddress, addressTitle)
                    val placeOrderRequest = PlaceOrderRequest(
                        totalAmount,
                        deliveryAddress,
                        itemList,
                        payment,
                        userId.toInt()
                    )
                    presenter.placeOrder(placeOrderRequest)
                }

            }

        }

    }


    override fun setResult(message: String) {
        cartDao.clearTable()
        val action = CheckoutFragmentDirections.finishOrderAction()
        currentView.findNavController().navigate(action)
    }

    override fun onLoad(isLoading: Boolean) {
        if(isLoading){
            requireActivity().findViewById<CircularProgressIndicator>(R.id.circularProgressBar)?.visibility = View.VISIBLE
        } else {
            requireActivity().findViewById<CircularProgressIndicator>(R.id.circularProgressBar)?.visibility = View.GONE
        }
    }
}