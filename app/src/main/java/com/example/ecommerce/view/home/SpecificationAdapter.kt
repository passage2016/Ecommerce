package com.example.ecommerce.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.model.remote.data.productDetail.Specification


class SpecificationAdapter(val infoArrayList: ArrayList<Specification>) :
    RecyclerView.Adapter<SpecificationAdapter.SpecificationHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecificationHolder {
        val mView: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.view_specification, parent, false)
        return SpecificationHolder(mView)
    }

    override fun onBindViewHolder(holder: SpecificationHolder, position: Int) {
        holder.apply {
            val info = infoArrayList.get(position)
            tvSpecificationTitle.text = info.title
            tvSpecificationSpecification.text = info.specification

        }
    }

    override fun getItemCount(): Int {
        return infoArrayList.size
    }

    inner class SpecificationHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvSpecificationTitle: TextView = view.findViewById(R.id.tv_specification_title)
        val tvSpecificationSpecification: TextView =
            view.findViewById(R.id.tv_specification_specification)
    }
}
