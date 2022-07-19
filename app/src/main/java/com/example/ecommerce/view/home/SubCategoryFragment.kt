package com.example.ecommerce.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import androidx.viewpager2.widget.ViewPager2
import com.example.ecommerce.R
import com.example.ecommerce.model.remote.ProductsVolleyHandler
import com.example.ecommerce.model.remote.SubCategoryVolleyHandler
import com.example.ecommerce.model.remote.data.products.Product
import com.example.ecommerce.model.remote.data.subCategory.SubCategoryResponse
import com.example.ecommerce.model.remote.data.subCategory.Subcategory
import com.example.ecommerce.presenter.products.ProductsPresenter
import com.example.ecommerce.presenter.subCategory.SubCategoryMVP
import com.example.ecommerce.presenter.subCategory.SubCategoryPresenter
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.tabs.TabLayoutMediator

class SubCategoryFragment : Fragment(), SubCategoryMVP.SubCategoryView {
    private val args: SubCategoryFragmentArgs by navArgs()
    lateinit var categoryID: String
    lateinit var subCategoryID: String
    private lateinit var subCategoryPresenter: SubCategoryPresenter
    private lateinit var productsPresenter: ProductsPresenter
    lateinit var subCategoryList: ArrayList<Subcategory>
    lateinit var productList: ArrayList<Product>
    lateinit var currentView: View

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


    }

    override fun setResult(subCategoryResponse: SubCategoryResponse?) {

        subCategoryResponse?.let {
            subCategoryList = subCategoryResponse.subcategories
                val adapter = SubCategoryViewPagerAdapter(this, subCategoryList)
                currentView.findViewById<ViewPager2>(R.id.vp_sub_category).adapter = adapter
                TabLayoutMediator(
                    currentView.findViewById(R.id.tl_sub_category),
                    currentView.findViewById(R.id.vp_sub_category)
                ) { tab, position ->
                    tab.text = subCategoryList[position].subcategory_name
                }.attach()

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