package com.example.ecommerce.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.model.remote.CategoryVolleyHandler
import com.example.ecommerce.model.remote.ProductsVolleyHandler
import com.example.ecommerce.model.remote.SubCategoryVolleyHandler
import com.example.ecommerce.model.remote.data.category.CategoryResponse
import com.example.ecommerce.model.remote.data.products.Product
import com.example.ecommerce.model.remote.data.products.ProductsResponse
import com.example.ecommerce.model.remote.data.subCategory.SubCategoryResponse
import com.example.ecommerce.model.remote.data.subCategory.Subcategory
import com.learning.mvpregistrationapp.presenter.category.*

class SubCategoryFragment: Fragment(), SubCategoryMVP.SubCategoryView, ProductsMVP.ProductsView {
    private val args : SubCategoryFragmentArgs by navArgs()
    lateinit var categoryID: String
    lateinit var subCategoryID: String
    private lateinit var subCategoryPresenter: SubCategoryPresenter
    private lateinit var productsPresenter: ProductsPresenter
    lateinit var subCategoryList: ArrayList<Subcategory>
    lateinit var subCategoryAdapter: SubCategoryAdapter
    lateinit var productList: ArrayList<Product>
    lateinit var productAdapter: ProductsAdapter
    lateinit var currentView:View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_sub_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        subCategoryPresenter = SubCategoryPresenter(SubCategoryVolleyHandler(view.context), this)
        categoryID = args.categoryId
        subCategoryPresenter.getSubCategory(categoryID)




        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

    }

    override fun setResult(subCategoryResponse: SubCategoryResponse?) {

        subCategoryResponse?.let {
            subCategoryList = subCategoryResponse.subcategories
            currentView?.let {
                Log.e("subCategoryList", "${subCategoryList}")
                subCategoryAdapter = SubCategoryAdapter(this, subCategoryList)
                currentView.findViewById<RecyclerView>(R.id.rv_sub_category).layoutManager = GridLayoutManager(currentView.context, 2)
                currentView.findViewById<RecyclerView>(R.id.rv_sub_category).adapter = subCategoryAdapter
                productsPresenter = ProductsPresenter(ProductsVolleyHandler(currentView.context), this)
                if(subCategoryList.size > 0){
                    subCategoryID = subCategoryList.get(0).subcategory_id
                    productsPresenter.getProducts(subCategoryID)
                }

            }
        }


    }

    fun setSubCategoryId(subCategoryId: String){
        productsPresenter = ProductsPresenter(ProductsVolleyHandler(currentView.context), this)
        subCategoryID = subCategoryId
        productsPresenter.getProducts(subCategoryID)
    }

    override fun setResult(productsResponse: ProductsResponse?) {
        productsResponse?.let {
            productList = productsResponse.products
            currentView?.let {
                Log.e("productList", "${productList}")
                productAdapter = ProductsAdapter(currentView.context, productList)
                currentView.findViewById<RecyclerView>(R.id.rv_products).layoutManager = LinearLayoutManager(currentView.context)
                currentView.findViewById<RecyclerView>(R.id.rv_products).adapter = productAdapter
            }
        }
    }

    override fun onLoad(isLoading: Boolean) {
    }
}