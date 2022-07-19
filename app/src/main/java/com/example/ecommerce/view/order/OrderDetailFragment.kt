package com.example.ecommerce.view.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.ecommerce.R
import com.example.ecommerce.model.remote.OrderVolleyHandler
import com.example.ecommerce.model.remote.data.orderDetail.OrderDetailResponse
import com.example.ecommerce.presenter.getOrderDetail.GetOrderDetailMVP
import com.example.ecommerce.presenter.getOrderDetail.GetOrderDetailPresenter
import com.google.android.material.progressindicator.CircularProgressIndicator

class OrderDetailFragment : Fragment(), GetOrderDetailMVP.GetOrderDetailView {
    private val args: OrderDetailFragmentArgs by navArgs()
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
                val tvOrderDetailId: TextView =
                    currentView.findViewById(R.id.tv_order_detail_id)
                val tvOrderDetailState: TextView =
                    currentView.findViewById(R.id.tv_order_detail_state)
                val tvOrderDetailTotalAmount: TextView =
                    currentView.findViewById(R.id.tv_order_detail_total_amount)
                val tvOrderDetailDeliveryAddressTitleInfo: TextView =
                    currentView.findViewById(R.id.tv_order_detail_delivery_address_title_info)
                val tvOrderDetailDeliveryAddressAddressInfo: TextView =
                    currentView.findViewById(R.id.tv_order_detail_delivery_address_address_info)
                val tvOrderDetailPaymentInfo: TextView =
                    currentView.findViewById(R.id.tv_order_detail_payment_info)

                tvOrderDetailDeliveryAddressTitleInfo.text = order.address_title
                tvOrderDetailDeliveryAddressAddressInfo.text = order.address
                tvOrderDetailTotalAmount.text = order.bill_amount
                tvOrderDetailPaymentInfo.text = order.payment_method
                tvOrderDetailState.text = order.order_status
                tvOrderDetailId.text = "#"+order.order_id

            }
        }


    }

    override fun onLoad(isLoading: Boolean) {
        if(isLoading){
            requireActivity().findViewById<CircularProgressIndicator>(R.id.circularProgressBar)?.visibility = View.VISIBLE
        } else {
            requireActivity().findViewById<CircularProgressIndicator>(R.id.circularProgressBar)?.visibility = View.GONE
        }
    }
}