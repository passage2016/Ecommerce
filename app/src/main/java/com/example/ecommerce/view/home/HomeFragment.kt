package com.example.ecommerce.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.model.remote.CategoryVolleyHandler
import com.example.ecommerce.model.remote.data.category.Category
import com.example.ecommerce.model.remote.data.category.CategoryResponse
import com.example.ecommerce.presenter.category.CategoryMVP
import com.example.ecommerce.presenter.category.CategoryPresenter

class HomeFragment : Fragment(), CategoryMVP.CategoryView {
    private lateinit var presenter: CategoryPresenter
    lateinit var adapter: ShopCategoryAdapter
    lateinit var categoryList: ArrayList<Category>
    lateinit var currentView: View


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        presenter = CategoryPresenter(CategoryVolleyHandler(view.context), this)
        presenter.getCategory()

    }


    override fun setResult(categoryResponse: CategoryResponse?) {
        categoryResponse?.let {
            categoryList = categoryResponse.categories
            currentView?.let {
                adapter = ShopCategoryAdapter(currentView.context, categoryList)
                currentView.findViewById<RecyclerView>(R.id.rv_home).layoutManager =
                    GridLayoutManager(currentView.context, 2)
                currentView.findViewById<RecyclerView>(R.id.rv_home).adapter = adapter
            }
        }


    }

    override fun onLoad(isLoading: Boolean) {
    }
}