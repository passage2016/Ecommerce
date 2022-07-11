package com.example.ecommerce.view.address

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.model.remote.data.address.Addresse


class AddresseAdapter(private val context: Context, val infoArrayList:ArrayList<Addresse>):
    RecyclerView.Adapter<AddresseAdapter.AddresseHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddresseHolder {
        val mView: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_address, parent, false)
        return AddresseHolder(mView)
    }
    override fun onBindViewHolder(holder: AddresseHolder, position: Int) {
        holder.apply {
            val info = infoArrayList.get(position)
            tvAddressTitle.text = info.title
            tvAddressAddress.text = info.address

        }
    }

    override fun getItemCount(): Int {
        return infoArrayList.size
    }



    inner class AddresseHolder(val view: View): RecyclerView.ViewHolder(view){
        val tvAddressTitle: TextView = view.findViewById(R.id.tv_address_title)
        val tvAddressAddress: TextView = view.findViewById(R.id.tv_address_address)
    }
}
