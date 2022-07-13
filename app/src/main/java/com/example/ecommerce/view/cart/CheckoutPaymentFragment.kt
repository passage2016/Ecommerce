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
import androidx.navigation.fragment.navArgs
import com.example.ecommerce.R
import com.example.ecommerce.model.local.CartDao
import com.example.ecommerce.model.remote.OrderVolleyHandler
import com.example.ecommerce.model.remote.data.placeOrderRequest.DeliveryAddress
import com.example.ecommerce.model.remote.data.placeOrderRequest.Item
import com.example.ecommerce.model.remote.data.placeOrderRequest.PlaceOrderRequest
import com.example.ecommerce.presenter.placeOrder.PlaceOrderMVP
import com.example.ecommerce.presenter.placeOrder.PlaceOrderPresenter
import com.example.ecommerce.view.LoginActivity
import com.example.ecommerce.view.cart.CheckoutDeliverFragment.Companion.ADDRESS_ADDRESS
import com.example.ecommerce.view.cart.CheckoutDeliverFragment.Companion.ADDRESS_TITLE

class CheckoutPaymentFragment : Fragment() {
    lateinit var currentView: View
    private lateinit var cartDao: CartDao
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var payment: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_checkout_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        cartDao = CartDao(view.context)
        sharedPreferences = this.requireActivity().getSharedPreferences(
            LoginActivity.ACCOUNT_INFO_FILE_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()

        payment = "COD"
        val group = view.findViewById<RadioGroup>(R.id.rg_choose_payment)
        group.setOnCheckedChangeListener { group: RadioGroup, checkId: Int ->
            val checkRadioButton = group.findViewById<RadioButton>(group.checkedRadioButtonId)
            checkRadioButton?.let {
                when (checkRadioButton.id) {
                    R.id.rbtn_cod -> {
                        payment =  "COD"
                    }
                    R.id.rbtn_bank_card -> {
                        payment =  "Paypal"
                    }
                    R.id.rbtn_bank_card -> {
                        payment =  "Bank Card"
                    }
                    R.id.rbtn_internet -> {
                        payment =  "Internet"
                    }
                    else -> {
                        payment =  "COD"
                    }
                }
            }

        }

        val btnPaymentNext: Button = currentView.findViewById(R.id.btn_payment_next)
        btnPaymentNext.setOnClickListener {
            editor.putString(PAYMENT, payment)
            editor.apply()
            (this.parentFragment as CheckoutFragment).nextPager()
        }

    }

    companion object {
        const val PAYMENT = "payment"
    }

}