package com.itzik.ui_layer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.itzik.commons.datamodels.BankData
import com.itzik.commons.datamodels.Definitions
import com.itzik.ui_layer.adapters.BankAdapter
import com.itzik.ui_layer.databinding.ActivityBankBinding
import com.itzik.ui_layer.viewmodels.BankViewModel

class BankActivity: AppCompatActivity(){

    private lateinit var viewModel: BankViewModel
    private lateinit var binding: ActivityBankBinding
    private lateinit var bankAdapter: BankAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bank)
        showRefresh()
        viewModel = ViewModelProviders.of(this).get(BankViewModel::class.java)
        binding.viewModel = viewModel
        createAdapter()

        viewModel.getBankList().observe(this, Observer<List<BankData>> { bankslist ->
            bankslist.let {
                stopRefresh()
                updateBankList(it)
            }
        })

        viewModel.getSelectBank().observe(this, Observer<BankData>{ bankData ->
            Log.d(TAG,"SELECT BANK = ${bankData}")
            launchBankInfoActivty(bankData)
        })

        setupPermissions()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            INTERNET_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    viewModel.fetchBankList()
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
        }
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.INTERNET
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to Internet denied")
            makeRequest()
        } else {
            Log.i(TAG, "Permission to Internet granted")
            viewModel.fetchBankList()
        }
    }

    private fun launchBankInfoActivty(bankData: BankData){
        intent = Intent(this, BankInfoActivity::class.java)
        intent.putExtra(Definitions.KEY_BANK_DATA, bankData)
        startActivity(intent)
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.INTERNET),
            INTERNET_REQUEST_CODE
        )
    }

    private fun updateBankList(items: List<BankData>) {
        bankAdapter.updateBankList(items)
        Log.d(TAG, "updatePeopleList")
    }

    private fun showRefresh() {
        // binding.progressBar.visibility = View.VISIBLE
        binding.swipeRefresh.setRefreshing(true)
        binding.swipeRefresh.setOnRefreshListener(CallbackRefresh())
    }

    private fun stopRefresh() {
        //binding.progressBar.visibility = View.GONE
        binding.swipeRefresh.setRefreshing(false)
    }

    private fun createAdapter() {
        bankAdapter = BankAdapter(viewModel)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = bankAdapter
        Log.d(TAG, "createAdapter")
    }

    private inner class CallbackRefresh: SwipeRefreshLayout.OnRefreshListener{
        override fun onRefresh() {
            viewModel.fetchBankList()
            Log.d(TAG,"onRefresh")
        }

    }


    companion object{
        private val TAG: String = BankActivity::class.java.simpleName
        private val INTERNET_REQUEST_CODE = 101
    }

}