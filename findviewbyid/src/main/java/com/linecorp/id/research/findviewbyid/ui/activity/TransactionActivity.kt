package com.linecorp.id.research.findviewbyid.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.linecorp.id.research.findviewbyid.ui.adapter.TransactionFragmentAdapter
import com.linecorp.id.research.findviewbyid.R

class TransactionActivity : AppCompatActivity() {

    private val transactionFragmentAdapter: TransactionFragmentAdapter = TransactionFragmentAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        initViewPager()
    }

    private fun initViewPager() {
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = transactionFragmentAdapter
    }
}
