package com.example.ecommerce.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.model.remote.ProductsVolleyHandler
import com.example.ecommerce.model.remote.data.subCategory.Subcategory
import com.learning.mvpregistrationapp.presenter.category.ProductsPresenter


class SubCategoryAdapter(private val subCategoryFragment: SubCategoryFragment, val infoArrayList:ArrayList<Subcategory>):
    RecyclerView.Adapter<SubCategoryAdapter.SubCategoryHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        communicator = context as Communicator
        val mView: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_sub_category, parent, false)
        return SubCategoryHolder(mView)
    }
    override fun onBindViewHolder(holder: SubCategoryHolder, position: Int) {
        holder.apply {
            val info = infoArrayList.get(position)
            tvSubCategory.text = info.subcategory_name



            itemView.setOnClickListener {
//                communicator.passSubCategoryId(info.subcategory_id)
                val productsPresenter = subCategoryFragment.context?.let { it1 ->
                    ProductsVolleyHandler(
                        it1
                    )
                }?.let { it2 -> ProductsPresenter(it2, subCategoryFragment) }
                productsPresenter?.getProducts(info.subcategory_id)

            }
        }
    }

    override fun getItemCount(): Int {
        return infoArrayList.size
    }



    inner class SubCategoryHolder(val view: View): RecyclerView.ViewHolder(view){
        val tvSubCategory: TextView = view.findViewById(R.id.tv_sub_category)
    }
}
