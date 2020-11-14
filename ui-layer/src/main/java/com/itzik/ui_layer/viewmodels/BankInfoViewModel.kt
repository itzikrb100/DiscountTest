package com.itzik.ui_layer.viewmodels

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.bl.repository.BankRepository
import com.itzik.business_logic_layer.networkRequest.ResponseEvent
import com.itzik.commons.datamodels.BankData
import com.itzik.commons.datamodels.BankInfoData
import com.itzik.commons.datamodels.Definitions

class BankInfoViewModel (application: Application): AndroidViewModel(application) {

    private val viewDataReady = MediatorLiveData<ViewActivityData>()
    private val dataReady = MediatorLiveData<BankData>()
    private val callback: Callback = Callback(viewDataReady)

    private var bankData: BankData? = null

    override fun onCleared() {

    }


    fun getViewDataReady(): LiveData<ViewActivityData> {
        return viewDataReady
    }

    fun getDataReady(): LiveData<BankData> {
        return dataReady
    }

    fun proccessIntentwithData(intent: Intent){
       bankData = intent.getParcelableExtra(Definitions.KEY_BANK_DATA)
       Log.d(TAG," proccessIntentwithData: bankData = $bankData")
    }

    fun fetchInfoTimeSeries(interval: String){
        BankRepository.getInstance().requestList(bankData!!, interval, callback)
    }


    private class Callback(val viewData: MediatorLiveData<ViewActivityData>) : ResponseEvent{
        override fun <T> onResponse(response: T, codeStatus: Int) {
            Log.d(TAG,"$response")
            Log.d(TAG, "code status = $codeStatus")
            viewData.postValue(ViewActivityData(response as List<BankInfoData>))
        }
    }


    data class  ViewActivityData(val bankinfodataList: List<BankInfoData>)

    companion object {
        private val TAG: String = BankInfoViewModel::class.java.simpleName
    }
}