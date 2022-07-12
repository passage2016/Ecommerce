package com.example.ecommerce.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.R
import com.example.ecommerce.model.remote.data.productDetail.Image
import com.learning.mvpregistrationapp.model.remote.Constants

class ProductDetailImageAdapter(val currentView: View, private val imageList: ArrayList<Image>) :
    RecyclerView.Adapter<ProductDetailImageAdapter.ViewPagerViewHolder>() {
    inner class ViewPagerViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(position: Int) {
            Glide.with(currentView)
                .load(Constants.BASE_IMAGE_URL + imageList.get(position).image)
                .into(view.findViewById(R.id.iv_product_detail_image))


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_product_deatil_image, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}