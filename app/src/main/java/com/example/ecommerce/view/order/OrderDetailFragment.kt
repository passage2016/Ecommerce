package com.example.ecommerce.view.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.model.local.CartDao
import com.example.ecommerce.model.local.CartItem
import com.example.ecommerce.model.remote.OrderVolleyHandler
import com.example.ecommerce.model.remote.ProductDetailVolleyHandler
import com.example.ecommerce.model.remote.data.orderDetail.OrderDetailResponse
import com.example.ecommerce.model.remote.data.productDetail.ProductDetailResponse
import com.example.ecommerce.model.remote.data.productDetail.Specification
import com.example.ecommerce.view.home.ProductFragmentArgs
import com.example.ecommerce.view.home.SpecificationAdapter
import com.learning.mvpregistrationapp.model.remote.Constants
import com.learning.mvpregistrationapp.presenter.category.GetOrderDetailMVP
import com.learning.mvpregistrationapp.presenter.category.GetOrderDetailPresenter
import com.learning.mvpregistrationapp.presenter.category.ProductDetailPresenter

class OrderDetailFragment: Fragment(), GetOrderDetailMVP.GetOrderDetailView {
    private val args : OrderDetailFragmentArgs by navArgs()
    private lateinit var presenter: GetOrderDetailPresenter
    lateinit var currentView: View
    lateinit var orderID: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_order_detail, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        presenter = GetOrderDetailPresenter(OrderVolleyHandler(view.context), this)
        orderID = args.orderId
        presenter.getOrderDetail(orderID)

    }



    override fun setResult(orderDetailResponse: OrderDetailResponse?) {

        orderDetailResponse?.let {
            val order = orderDetailResponse.order
            currentView?.let {
                Log.e("order", "${order}")
                val tvOrderDetailAddressTitle: TextView = currentView.findViewById(R.id.tv_order_detail_address_title)
                val tvOrderDetailAddress: TextView = currentView.findViewById(R.id.tv_order_detail_address)
                val tvOrderDetailBillAmount: TextView = currentView.findViewById(R.id.tv_order_detail_bill_amount)
                val tvOrderDetailPaymentMethod: TextView = currentView.findViewById(R.id.tv_order_detail_payment_method)
                val tvOrderDetailStatus: TextView = currentView.findViewById(R.id.tv_order_detail_status)
                val tvOrderDetailDate: TextView = currentView.findViewById(R.id.tv_order_detail_date)

                tvOrderDetailAddressTitle.text = order.address_title
                tvOrderDetailAddress.text = order.address
                tvOrderDetailBillAmount.text = order.bill_amount
                tvOrderDetailPaymentMethod.text = order.payment_method
                tvOrderDetailStatus.text = order.order_status
                tvOrderDetailDate.text = order.order_date

            }
        }


    }

    override fun onLoad(isLoading: Boolean) {
    }
}