package com.example.ecommerce.view.cart

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.R
import com.example.ecommerce.model.local.CartDao
import com.example.ecommerce.model.local.CartItem
import com.learning.mvpregistrationapp.model.remote.Constants.BASE_IMAGE_URL


class CartItemAdapter(private val context: Context, val infoArrayList:ArrayList<CartItem>):
    RecyclerView.Adapter<CartItemAdapter.CartItemHolder>() {
    private lateinit var cartDao: CartDao

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemHolder {
        val mView: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cart, parent, false)
        cartDao = CartDao(parent.getContext())
        return CartItemHolder(mView)
    }
    override fun onBindViewHolder(holder: CartItemHolder, position: Int) {
        holder.apply {
            val info = infoArrayList.get(position)
            tvCartName.text = info.productName
            tvCartTotalPrice.text = (info.price * info.count).toString()
            tvCartDescription.text = info.description
            tvCartPrice.text = "$ ${info.price}"
            Log.e("product_image_url", "${BASE_IMAGE_URL + info.productImageUrl}")

            Glide.with(context)
                .load(BASE_IMAGE_URL + info.productImageUrl)
                .into(ivCartIm)

            var cart =cartDao.getCartItemByProductId(info.productId.toInt())
            if(cart != null && cart!!.count > 0){
                tvCartCount.text = cart!!.count.toString()

            }

            ibCartSub.setOnClickListener {
                if (cart != null) {
                    if(cart!!.count < 2){


                        cart!!.cartId?.let { it1 ->
                            if(cartDao.deleteCartItem(it1)){
                                Log.e("Delete", "Delete cart id = ${cart!!.cartId} name = ${cart!!.productName} success")
                                notifyItemRemoved(position)
                                infoArrayList.removeAt(position)
                                notifyItemRangeChanged(position, infoArrayList.size)
                            }
                        }

                    } else {
                        cart!!.count = cart!!.count - 1
                        cartDao.updateCartItem(cart!!)
                        tvCartCount.text = cart!!.count.toString()
                    }
                }
            }
            ibCartAdd.setOnClickListener {
                if (cart != null) {
                    cart!!.count = cart!!.count + 1
                    cartDao.updateCartItem(cart!!)
                    tvCartCount.text = cart!!.count.toString()
                }
            }

            itemView.setOnClickListener {
                Log.e("product_id", "${info.productId}")
                val action = CartFragmentDirections.cartProductDetailAction(info.productId)
                it.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return infoArrayList.size
    }



    inner class CartItemHolder(val view: View): RecyclerView.ViewHolder(view){
        val ivCartIm: ImageView = view.findViewById(R.id.iv_cart_im)
        val tvCartName: TextView = view.findViewById(R.id.tv_cart_name)
        val tvCartTotalPrice: TextView = view.findViewById(R.id.tv_cart_total_price)
        val tvCartDescription: TextView = view.findViewById(R.id.tv_cart_description)
        val tvCartPrice: TextView = view.findViewById(R.id.tv_cart_price)
        val ibCartSub: ImageButton = view.findViewById(R.id.ib_cart_sub)
        val tvCartCount: TextView = view.findViewById(R.id.tv_cart_count)
        val ibCartAdd: ImageButton = view.findViewById(R.id.ib_cart_add)
    }
}
