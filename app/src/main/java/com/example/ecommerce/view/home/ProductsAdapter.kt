package com.example.ecommerce.view.home

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
import com.example.ecommerce.model.remote.data.products.Product
import com.learning.mvpregistrationapp.model.remote.Constants.BASE_IMAGE_URL


class ProductsAdapter(private val context: Context, val infoArrayList: ArrayList<Product>, val from: Int) :
    RecyclerView.Adapter<ProductsAdapter.ProductsHolder>() {
    private lateinit var cartDao: CartDao

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsHolder {
        val mView: View =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.view_products, parent, false)
        cartDao = CartDao(parent.getContext())
        return ProductsHolder(mView)
    }

    override fun onBindViewHolder(holder: ProductsHolder, position: Int) {
        holder.apply {
            val info = infoArrayList.get(position)
            tvProductsName.text = info.product_name
            rbProduct.rating = info.average_rating.toFloat()
            tvProductsDescription.text = info.description
            tvProductsPrice.text = "$ ${info.price}"
            Log.e("product_image_url", "${BASE_IMAGE_URL + info.product_image_url}")

            Glide.with(context)
                .load(BASE_IMAGE_URL + info.product_image_url)
                .into(ivProductsIm)

            var cart = cartDao.getCartItemByProductId(info.product_id.toInt())
            if (cart != null && cart!!.count > 0) {
                tvProductsAddToCart.visibility = View.GONE
                llProductsCount.visibility = View.VISIBLE
                tvProductsCount.text = cart!!.count.toString()

            }

            ibProductsSub.setOnClickListener {
                if (cart != null) {
                    if (cart!!.count < 2) {
                        cart!!.cartId?.let { it1 ->
                            if (cartDao.deleteCartItem(it1)) {
                                Log.e(
                                    "Delete",
                                    "Delete cart id = ${cart!!.cartId} name = ${cart!!.productName} success"
                                )
                            }

                        }
                        tvProductsAddToCart.visibility = View.VISIBLE
                        llProductsCount.visibility = View.GONE
                    } else {
                        cart!!.count = cart!!.count - 1
                        cartDao.updateCartItem(cart!!)
                        tvProductsCount.text = cart!!.count.toString()
                    }
                }
            }
            ibProductsAdd.setOnClickListener {
                if (cart != null) {
                    cart!!.count = cart!!.count + 1
                    cartDao.updateCartItem(cart!!)
                    tvProductsCount.text = cart!!.count.toString()
                }
            }
            tvProductsAddToCart.setOnClickListener {
                tvProductsAddToCart.visibility = View.GONE
                llProductsCount.visibility = View.VISIBLE
                val cartItem = CartItem(
                    null,
                    info.product_name,
                    info.product_id,
                    info.description,
                    info.price.toDouble(),
                    info.category_id,
                    info.sub_category_id,
                    info.product_image_url,
                    1
                )
                cartItem.cartId = cartDao.addCartItem(cartItem)
                if (cartItem.cartId != null && cartItem.cartId!! > 0) {
                    tvProductsCount.text = "1"
                    cart = cartDao.getCartItemByProductId(info.product_id.toInt())
                }

            }

            itemView.setOnClickListener {
                Log.e("product_id", "${info.product_id}")
                val bundle = Bundle()
                bundle.putString("product_id", info.product_id)
                if(from == 0){
                    val action = SubCategoryFragmentDirections.productDetailAction(info.product_id)
                    it.findNavController().navigate(action)
                } else {
                    val action = SearchProductFragmentDirections.productDetailAction(info.product_id)
                    it.findNavController().navigate(action)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return infoArrayList.size
    }


    inner class ProductsHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val ivProductsIm: ImageView = view.findViewById(R.id.iv_products_im)
        val tvProductsName: TextView = view.findViewById(R.id.tv_products_name)
        val rbProduct: RatingBar = view.findViewById(R.id.rb_product)
        val tvProductsDescription: TextView = view.findViewById(R.id.tv_products_description)
        val tvProductsPrice: TextView = view.findViewById(R.id.tv_products_price)
        val tvProductsAddToCart: TextView = view.findViewById(R.id.tv_products_add_to_cart)
        val llProductsCount: LinearLayout = view.findViewById(R.id.ll_products_count)
        val ibProductsAdd: ImageButton = view.findViewById(R.id.ib_products_add)
        val ibProductsSub: ImageButton = view.findViewById(R.id.ib_products_sub)
        val tvProductsCount: TextView = view.findViewById(R.id.tv_products_count)
    }
}
