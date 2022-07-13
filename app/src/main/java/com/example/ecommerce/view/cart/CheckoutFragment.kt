package com.example.ecommerce.view.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentCheckoutBinding
import com.example.ecommerce.model.local.CartDao
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CheckoutFragment:Fragment() {
    lateinit var currentView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view

        val adapter = CheckoutFragmentAdapter(this, 4)
        view.findViewById<ViewPager2>(R.id.vp_checkout_view_pager).adapter = adapter
        val tabString = arrayOf("Cart Items", "Delivery", "Payment", "Summary")
        TabLayoutMediator(view.findViewById<TabLayout>(R.id.tl_checkout_tab), view.findViewById<ViewPager2>(R.id.vp_checkout_view_pager)){ tab, position->
            tab.text = tabString[position]

        }.attach()

    }

    fun nextPager(){
        currentView.findViewById<ViewPager2>(R.id.vp_checkout_view_pager).currentItem = currentView.findViewById<ViewPager2>(R.id.vp_checkout_view_pager).currentItem + 1
    }

}