package com.example.ecommerce.view.cart

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CheckoutFragmentAdapter(checkoutFragment: CheckoutFragment, private val totalCount: Int):
    FragmentStateAdapter(checkoutFragment) {
    override fun getItemCount(): Int {
        return totalCount
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->CheckoutCartItemFragment()
            1->CheckoutDeliverFragment()
            2->CheckoutPaymentFragment()
            3->CheckoutSummaryFragment()
            else->CheckoutCartItemFragment()
        }
    }


}