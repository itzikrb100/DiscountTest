package com.example.bl.repository

import android.util.Log
import com.example.bl.networkRequest.NetworkRequestManager
import com.itzik.business_logic_layer.networkRequest.ResponseEvent
import com.itzik.business_logic_layer.networkRequest.repository.Repository
import com.itzik.commons.datamodels.BankData

class BankRepository: Repository {

    private val TAG: String = BankRepository::class.java.simpleName

    // avoid to create MovieRepository
    private constructor()
    private var networkRequestManager: NetworkRequestManager

    init {
        Log.d(TAG,"INIT")
        networkRequestManager = NetworkRequestManager()
    }

    fun closeRepository(){
        networkRequestManager.cleanup()
    }




    companion object {
        private  var INSTANCE: BankRepository? = null


        @Synchronized fun getInstance(): BankRepository{
           if(INSTANCE == null)
                INSTANCE = BankRepository()

           return INSTANCE!!
        }
    }

    override fun requestList(bankData: BankData, interval: String,resEvent: ResponseEvent) {
        requestBankList(bankData.stk!!, interval, resEvent)
    }


    private fun requestBankList(symbole: String, interval: String,event: ResponseEvent){
        Log.d(TAG,"requestBankList: symbole - $symbole, interval - $interval")
        networkRequestManager.getBankInfoRequestNetwork(symbole, interval, event)
    }


}