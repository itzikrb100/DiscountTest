package com.itzik.business_logic_layer.networkRequest.repository

import com.itzik.business_logic_layer.networkRequest.ResponseEvent
import com.itzik.commons.datamodels.BankData

interface Repository{
    fun requestList(bankData: BankData, interval:String ,resEvent: ResponseEvent)
}