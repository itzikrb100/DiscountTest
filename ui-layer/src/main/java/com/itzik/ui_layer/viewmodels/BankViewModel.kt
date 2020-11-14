package com.itzik.ui_layer.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.bl.repository.BankRepository
import com.itzik.business_logic_layer.networkRequest.ResponseEvent
import com.itzik.business_logic_layer.networkRequest.repository.Repository
import com.itzik.commons.datamodels.BankData
import com.itzik.commons.datamodels.BankGenerator
import com.itzik.commons.datamodels.ResponseData

class BankViewModel(application: Application) : AndroidViewModel(application) {

    private val bankList = MediatorLiveData<List<BankData>>()
    private val bankSelectItem = MediatorLiveData<BankData>()

    override fun onCleared() {

    }

    fun getBankList(): LiveData<List<BankData>> {
        return bankList
    }

    fun getSelectBank(): LiveData<BankData> {
        return bankSelectItem
    }

    fun fetchBankList() {
        bankList.value = BankGenerator.makeBankList()
    }

    fun onClickItem(itemSelect: BankData) {
        Log.d(TAG,"onClickItem: ITEM = ${itemSelect}")
        bankSelectItem.value = itemSelect
    }

    private object response : ResponseEvent {
        override fun <ResponseData> onResponse(response: ResponseData, codeStatus: Int) {
               Log.d(TAG,"$response")
        }
    }

    companion object {
        private val TAG: String = BankViewModel::class.java.simpleName
    }
}