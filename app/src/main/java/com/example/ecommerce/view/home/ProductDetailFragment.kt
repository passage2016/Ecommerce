package com.example.ecommerce.view.home

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.ecommerce.R
import com.example.ecommerce.model.local.CartDao
import com.example.ecommerce.model.local.CartItem
import com.example.ecommerce.model.remote.ProductDetailVolleyHandler
import com.example.ecommerce.model.remote.data.productDetail.ProductDetailResponse
import com.example.ecommerce.model.remote.data.productDetail.Specification
import com.example.ecommerce.presenter.productDetail.ProductDetailMVP
import com.example.ecommerce.presenter.productDetail.ProductDetailPresenter
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.learning.mvpregistrationapp.model.remote.Constants.BASE_IMAGE_URL


class ProductDetailFragment : Fragment(), ProductDetailMVP.ProductDetailView {
    private val args: ProductDetailFragmentArgs by navArgs()
    private lateinit var presenter: ProductDetailPresenter
    lateinit var currentView: View
    lateinit var productID: String
    private lateinit var cartDao: CartDao
    lateinit var uri: Uri
    var touchDownX: Float = 0.0F
    var touchUpX: Float = 0.0F

    lateinit var specificationList: ArrayList<Specification>
    lateinit var specificationAdapter: SpecificationAdapter
    var index = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartDao = CartDao(view.context)
        currentView = view
        presenter = ProductDetailPresenter(ProductDetailVolleyHandler(view.context), this)
        productID = args.productId
        presenter.getProductDetail(productID)

    }


    override fun setResult(productDetailResponse: ProductDetailResponse?) {

        productDetailResponse?.let {
            val product = productDetailResponse.product
            currentView?.let {
                Log.e("product", "${product}")
                val tvProductDetailName: TextView =
                    currentView.findViewById(R.id.tv_product_detail_name)
                val rbProductDetail: RatingBar = currentView.findViewById(R.id.rb_product_detail)
                val tvProductDetailDescription: TextView =
                    currentView.findViewById(R.id.tv_product_detail_description)
                val vpProductDetailImageView: ViewPager2 =
                    currentView.findViewById(R.id.vp_product_detail_image_view)
                val tvProductDetailPrice: TextView =
                    currentView.findViewById(R.id.tv_product_detail_price)
                val tvProductDetailAddToCart: TextView =
                    currentView.findViewById(R.id.tv_product_detail_add_to_cart)
                val llProductDetailCount: LinearLayout =
                    currentView.findViewById(R.id.ll_product_detail_count)
                val ibProductDetailSub: ImageButton =
                    currentView.findViewById(R.id.ib_product_detail_sub)
                val tvProductDetailCount: TextView =
                    currentView.findViewById(R.id.tv_product_detail_count)
                val ibProductDetailAdd: ImageButton =
                    currentView.findViewById(R.id.ib_product_detail_add)
                val rvProductDetailSpecifications: RecyclerView =
                    currentView.findViewById(R.id.rv_product_detail_specifications)
                val rvProductDetailReview: RecyclerView =
                    currentView.findViewById(R.id.rv_product_detail_review)

                tvProductDetailName.text = product.product_name
                rbProductDetail.rating = product.average_rating.toFloat()
                tvProductDetailDescription.text = product.description
                tvProductDetailPrice.text = "${product.price}"

                val adapter = ProductDetailImageAdapter(currentView, product.images)
                vpProductDetailImageView.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                vpProductDetailImageView.adapter = adapter

                var cart = cartDao.getCartItemByProductId(product.product_id.toInt())
                if (cart != null && cart!!.count > 0) {
                    tvProductDetailAddToCart.visibility = View.GONE
                    llProductDetailCount.visibility = View.VISIBLE
                    tvProductDetailCount.text = cart!!.count.toString()

                }
                ibProductDetailSub.setOnClickListener {
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
                            tvProductDetailAddToCart.visibility = View.VISIBLE
                            llProductDetailCount.visibility = View.GONE
                        } else {
                            cart!!.count = cart!!.count - 1
                            cartDao.updateCartItem(cart!!)
                            tvProductDetailCount.text = cart!!.count.toString()
                        }
                    }
                }
                ibProductDetailAdd.setOnClickListener {
                    if (cart != null) {
                        cart!!.count = cart!!.count + 1
                        cartDao.updateCartItem(cart!!)
                        tvProductDetailCount.text = cart!!.count.toString()
                    }
                }
                tvProductDetailAddToCart.setOnClickListener {
                    tvProductDetailAddToCart.visibility = View.GONE
                    llProductDetailCount.visibility = View.VISIBLE
                    val cartItem = CartItem(
                        null,
                        product.product_name,
                        product.product_id,
                        product.description,
                        product.price.toDouble(),
                        product.category_id,
                        product.sub_category_id,
                        product.product_image_url,
                        1
                    )
                    cartItem.cartId = cartDao.addCartItem(cartItem)
                    if (cartItem.cartId != null && cartItem.cartId!! > 0) {
                        tvProductDetailCount.text = "1"
                        cart = cartDao.getCartItemByProductId(product.product_id.toInt())
                    }

                }
                rvProductDetailSpecifications

                specificationList = product.specifications
                specificationAdapter = SpecificationAdapter(specificationList)
                rvProductDetailSpecifications.layoutManager =
                    LinearLayoutManager(currentView.context)
                rvProductDetailSpecifications.adapter = specificationAdapter
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