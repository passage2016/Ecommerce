package com.example.ecommerce.view.cart

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ecommerce.R
import com.example.ecommerce.model.local.CartDao
import com.example.ecommerce.model.remote.AddressVolleyHandler
import com.example.ecommerce.model.remote.OrderVolleyHandler
import com.example.ecommerce.model.remote.data.placeOrderRequest.DeliveryAddress
import com.example.ecommerce.model.remote.data.placeOrderRequest.Item
import com.example.ecommerce.model.remote.data.placeOrderRequest.PlaceOrderRequest
import com.example.ecommerce.view.LoginActivity
import com.example.ecommerce.view.home.ProductFragmentArgs
import com.learning.mvpregistrationapp.presenter.category.GetAddressPresenter
import com.learning.mvpregistrationapp.presenter.category.PlaceOrderMVP
import com.learning.mvpregistrationapp.presenter.category.PlaceOrderPresenter

class ChoosePaymentFragment: Fragment(), PlaceOrderMVP.PlaceOrderView {
    private val args : ChoosePaymentFragmentArgs by navArgs()
    lateinit var currentView: View
    private lateinit var presenter: PlaceOrderPresenter
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    private lateinit var cartDao: CartDao
    var payment = "COD"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_choose_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        presenter = PlaceOrderPresenter(OrderVolleyHandler(view.context), this)
        cartDao = CartDao(view.context)

        val group = view.findViewById<RadioGroup>(R.id.rg_choose_payment)
        group.setOnCheckedChangeListener{
                group: RadioGroup, checkId: Int ->
            val checkRadioButton = group.findViewById<RadioButton>(group.checkedRadioButtonId)
            checkRadioButton?.let {
                when (checkRadioButton.id) {
                    R.id.rbtn_cod -> {
                        payment = "COD"
                    }
                    R.id.rbtn_paypol -> {
                        payment = "Paypal"
                    }
                    R.id.rbtn_bank_card -> {
                        payment = "Bank Card"
                    }
                    else -> {
                        payment = "COD"
                    }
                }
            }

        }

        val btnFinishOrder = view.findViewById<Button>(R.id.btn_finish_order)
        btnFinishOrder.setOnClickListener {
            sharedPreferences = this.requireActivity().getSharedPreferences(LoginActivity.ACCOUNT_INFO_FILE_NAME, AppCompatActivity.MODE_PRIVATE)
            editor = sharedPreferences.edit()
            val userId = sharedPreferences.getString(LoginActivity.USER_ID, "-1")

            userId?.let {


                val cartItemList = cartDao.getAllCartItem()
                var itemList = ArrayList<Item>()
                var totalAmount: Int = 0
                for (i in 0 until cartItemList.size){
                    val cartItem = cartItemList.get(i)
                    totalAmount = totalAmount + (cartItem.price * cartItem.count).toInt()
                    itemList.add(Item(cartItem.cartId!!.toInt(), cartItem.count, cartItem.price.toInt()))
                }

                val deliveryAddress = DeliveryAddress(args.addressAddress, args.addressTitle)
                val placeOrderRequest = PlaceOrderRequest(totalAmount, deliveryAddress, itemList, payment, userId.toInt())
                presenter.placeOrder(placeOrderRequest)
            }

        }

    }

    override fun setResult(message: String) {
        Toast.makeText(currentView.context, message, Toast.LENGTH_SHORT).show()
        cartDao.clearTable()
        val action = ChoosePaymentFragmentDirections.finishOrderAction()
        currentView.findNavController().navigate(action)
    }

    override fun onLoad(isLoading: Boolean) {
    }
}