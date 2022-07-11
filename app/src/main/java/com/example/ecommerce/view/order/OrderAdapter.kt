package com.example.ecommerce.view.order

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.model.remote.data.order.Order


class OrderAdapter(private val context: Context, val infoArrayList: ArrayList<Order>) :
    RecyclerView.Adapter<OrderAdapter.OrderHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val mView: View =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.view_order, parent, false)
        return OrderHolder(mView)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        holder.apply {
            val info = infoArrayList.get(position)
            tvOrderTitle.text = info.address_title
            tvOrderAddress.text = info.address
            tvOrderAmount.text = info.bill_amount
            tvOrderPayment_method.text = info.payment_method
            tvOrderStatus.text = info.order_status
            tvOrderDate.text = info.order_date

            itemView.setOnClickListener {
                Log.e("order_id", "${info.order_id}")
                val action = OrderFragmentDirections.orderDetailAction(info.order_id)
                it.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return infoArrayList.size
    }


    inner class OrderHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvOrderTitle: TextView = view.findViewById(R.id.tv_order_title)
        val tvOrderAddress: TextView = view.findViewById(R.id.tv_order_address)
        val tvOrderAmount: TextView = view.findViewById(R.id.tv_order_amount)
        val tvOrderPayment_method: TextView = view.findViewById(R.id.tv_order_payment_method)
        val tvOrderStatus: TextView = view.findViewById(R.id.tv_order_status)
        val tvOrderDate: TextView = view.findViewById(R.id.tv_order_date)
    }
}
