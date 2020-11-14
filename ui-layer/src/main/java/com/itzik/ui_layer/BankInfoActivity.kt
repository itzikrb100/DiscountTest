package com.itzik.ui_layer

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.itzik.ui_layer.adapters.BankTimeSeriesAdapter
import com.itzik.ui_layer.databinding.ActivityInfoBankBinding
import com.itzik.ui_layer.viewmodels.BankInfoViewModel

class BankInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBankBinding
    private lateinit var viewModel: BankInfoViewModel
    private lateinit var bankTimeSeriesAdapter: BankTimeSeriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info_bank)
        viewModel = ViewModelProviders.of(this).get(BankInfoViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.proccessIntentwithData(intent)
        binding.radioButtons.setOnCheckedChangeListener(object :
            RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                val interval = when (checkedId) {
                    R.id.one ->  "1"
                    R.id.two ->  "5"
                    R.id.three -> "15"
                    R.id.four -> "30"
                    R.id.five -> "60"
                    else -> "1"

                }

                showProgress()
                viewModel.fetchInfoTimeSeries(interval)
            }
        })
        createAdapter()
        viewModel.getViewDataReady()
            .observe(this, Observer<BankInfoViewModel.ViewActivityData> { viewDataready ->
                bankTimeSeriesAdapter.updateBankInfoList(viewDataready.bankinfodataList)
                dismissProgress()
            })

        viewModel.getDataReady().observe(this, Observer { bankdata ->

        })
    }


    private fun createAdapter() {
        bankTimeSeriesAdapter = BankTimeSeriesAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = bankTimeSeriesAdapter
        Log.d(TAG, "createAdapter")
    }

    private fun showProgress() {
        binding.progressBar.visibility = VISIBLE
    }


    private fun dismissProgress() {
        binding.progressBar.visibility = GONE
    }


    override fun onDestroy() {
        super.onDestroy()
        dismissProgress()
    }

    companion object {
        private val TAG: String = BankActivity::class.java.simpleName
    }

}