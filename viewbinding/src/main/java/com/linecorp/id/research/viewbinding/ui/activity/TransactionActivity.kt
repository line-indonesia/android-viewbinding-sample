package com.linecorp.id.research.viewbinding.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.linecorp.id.research.viewbinding.databinding.ActivityTransactionBinding
import com.linecorp.id.research.viewbinding.ui.adapter.TransactionFragmentAdapter

class TransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionBinding
    private val transactionFragmentAdapter: TransactionFragmentAdapter = TransactionFragmentAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
    }

    private fun initViewPager() {
        binding.viewPager.adapter = transactionFragmentAdapter
    }
}
