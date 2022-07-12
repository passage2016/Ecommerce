package com.example.ecommerce.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.model.remote.ProductsVolleyHandler
import com.example.ecommerce.model.remote.SubCategoryVolleyHandler
import com.example.ecommerce.model.remote.data.products.Product
import com.example.ecommerce.model.remote.data.products.ProductsResponse
import com.example.ecommerce.model.remote.data.subCategory.SubCategoryResponse
import com.example.ecommerce.model.remote.data.subCategory.Subcategory
import com.example.ecommerce.presenter.products.ProductsMVP
import com.example.ecommerce.presenter.products.ProductsPresenter
import com.example.ecommerce.presenter.subCategory.SubCategoryMVP
import com.example.ecommerce.presenter.subCategory.SubCategoryPresenter

class ProductsFragment(val productList: ArrayList<Product>, val from: Int) : Fragment() {
    lateinit var productAdapter: ProductsAdapter
    lateinit var currentView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view

        Log.e("productList", "${productList}")
        productAdapter = ProductsAdapter(currentView.context, productList, from)
        currentView.findViewById<RecyclerView>(R.id.rv_products).layoutManager =
            LinearLayoutManager(currentView.context)
        currentView.findViewById<RecyclerView>(R.id.rv_products).adapter = productAdapter

    }

}