package com.example.ecommerce.view.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecommerce.model.remote.data.subCategory.Subcategory


class SubCategoryViewPagerAdapter(subCategoryFragment: SubCategoryFragment,private val subCategoryList: ArrayList<Subcategory>):
    FragmentStateAdapter(subCategoryFragment) {
    override fun getItemCount(): Int {
        return subCategoryList.size
    }

    override fun createFragment(position: Int): Fragment {
        return SubCategoryProductsFragment(subCategoryList[position].subcategory_id)
    }

}