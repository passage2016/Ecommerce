package com.example.ecommerce.view.cart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.R
import com.example.ecommerce.model.local.CartDao
import com.example.ecommerce.model.local.CartItem
import com.learning.mvpregistrationapp.model.remote.Constants.BASE_IMAGE_URL


class CheckoutCartItemAdapter(private val context: Context, val infoArrayList: ArrayList<CartItem>) :
    RecyclerView.Adapter<CheckoutCartItemAdapter.CartItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemHolder {
        val mView: View =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cart_sample, parent, false)
        return CartItemHolder(mView)
    }

    override fun onBindViewHolder(holder: CartItemHolder, position: Int) {
        holder.apply {
            val info = infoArrayList.get(position)
            tvCartSampleName.text = info.productName
            tvCartSampleAmount.text = (info.price * info.count).toString()
            tvCartSampleQuantity.text = info.count.toString()
            tvCartSamplePrice.text = "$ ${info.price}"
            Log.e("product_image_url", "${BASE_IMAGE_URL + info.productImageUrl}")

            Glide.with(context)
                .load(BASE_IMAGE_URL + info.productImageUrl)
                .into(ivCartSampleIm)

        }
    }

    override fun getItemCount(): Int {
        return infoArrayList.size
    }


    inner class CartItemHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val ivCartSampleIm: ImageView = view.findViewById(R.id.iv_cart_sample_im)
        val tvCartSampleName: TextView = view.findViewById(R.id.tv_cart_sample_name)
        val tvCartSamplePrice: TextView = view.findViewById(R.id.tv_cart_sample_price)
        val tvCartSampleQuantity: TextView = view.findViewById(R.id.tv_cart_sample_quantity)
        val tvCartSampleAmount: TextView = view.findViewById(R.id.tv_cart_sample_amount)
    }
}
