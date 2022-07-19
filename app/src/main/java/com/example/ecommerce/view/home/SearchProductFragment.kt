package com.example.ecommerce.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentSearchProductBinding
import com.example.ecommerce.model.remote.ProductsVolleyHandler
import com.example.ecommerce.model.remote.data.products.Product
import com.example.ecommerce.model.remote.data.products.ProductsResponse
import com.example.ecommerce.presenter.products.ProductsMVP
import com.example.ecommerce.presenter.products.ProductsPresenter
import com.google.android.material.progressindicator.CircularProgressIndicator

class SearchProductFragment : Fragment(), ProductsMVP.ProductsView {
    private lateinit var productsPresenter: ProductsPresenter
    lateinit var productList: ArrayList<Product>
    lateinit var currentView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_search_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        productsPresenter =
            ProductsPresenter(ProductsVolleyHandler(currentView.context), this)

        view.findViewById<ImageButton>(R.id.ib_search_close).setOnClickListener {
            view.findViewById<EditText>(R.id.et_search_query).setText("")
        }

        view.findViewById<ImageButton>(R.id.ib_search_search).setOnClickListener {
            productsPresenter.searchProducts(view.findViewById<EditText>(R.id.et_search_query).text.toString())
        }

    }

    override fun setResult(productsResponse: ProductsResponse?) {
        productsResponse?.let {
            productList = productsResponse.products
            currentView?.let {

                val productsFragment = ProductsFragment(productList, 1)
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.gl_search_products_fragment, productsFragment)
                    .commit()
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