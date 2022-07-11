package com.example.ecommerce.view.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.R
import com.example.ecommerce.model.remote.data.category.Category
import com.learning.mvpregistrationapp.model.remote.Constants.BASE_IMAGE_URL


class ShopCategoryAdapter(private val context: Context, val infoArrayList: ArrayList<Category>) :
    RecyclerView.Adapter<ShopCategoryAdapter.CategoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val mView: View =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.view_home, parent, false)
        return CategoryHolder(mView)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.apply {
            val info = infoArrayList.get(position)
            tvHomeTitle.text = info.category_name

            Glide.with(context)
                .load(BASE_IMAGE_URL + info.category_image_url)
                .into(ivHomeIm)
            itemView.setOnClickListener {
                Log.e("category_id", "${info.category_id}")
                val action = HomeFragmentDirections.nextAction(info.category_id)
                it.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return infoArrayList.size
    }


    inner class CategoryHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val ivHomeIm: ImageView = view.findViewById(R.id.iv_home_im)
        val tvHomeTitle: TextView = view.findViewById(R.id.tv_home_title)
    }
}
