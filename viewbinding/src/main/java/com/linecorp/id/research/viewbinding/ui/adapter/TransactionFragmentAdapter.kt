package com.linecorp.id.research.viewbinding.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.linecorp.id.research.viewbinding.ui.fragment.TransactionHistoryFragment

class TransactionFragmentAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = TOTAL_PAGE

    override fun createFragment(position: Int): Fragment {
        return TransactionHistoryFragment()
    }

    companion object {
        const val TOTAL_PAGE = 3
    }
}
