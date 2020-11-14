package com.itzik.testbankdiscount

import android.app.Application
import android.util.Log

class BDApp: Application() {


    private val TAG: String = BDApp::class.java.simpleName



    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"onCreate")
    }

}