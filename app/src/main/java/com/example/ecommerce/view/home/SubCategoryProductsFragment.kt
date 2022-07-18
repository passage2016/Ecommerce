package com.example.ecommerce.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.model.remote.ProductsVolleyHandler
import com.example.ecommerce.model.remote.data.products.Product
import com.example.ecommerce.model.remote.data.products.ProductsResponse
import com.example.ecommerce.presenter.products.ProductsMVP
import com.example.ecommerce.presenter.products.ProductsPresenter

class SubCategoryProductsFragment(val subCategoryId: String) : Fragment(),
    ProductsMVP.ProductsView {
    private lateinit var presenter: ProductsPresenter
    private lateinit var productList: ArrayList<Product>
    private lateinit var adapter: ProductsAdapter
    lateinit var currentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        presenter = ProductsPresenter(ProductsVolleyHandler(view.context), this)
        presenter.getProducts(subCategoryId)
    }

    override fun setResult(productsResponse: ProductsResponse?) {
        productsResponse?.let {
            productList = productsResponse.products
            currentView?.let {

                adapter = ProductsAdapter(currentView.context, productList, 0)
                val rvProducts: RecyclerView = currentView.findViewById(R.id.rv_products)
                rvProducts.layoutManager = LinearLayoutManager(context)
                rvProducts.adapter = adapter
            }
        }

    }

    override fun onLoad(isLoading: Boolean) {
    }
}