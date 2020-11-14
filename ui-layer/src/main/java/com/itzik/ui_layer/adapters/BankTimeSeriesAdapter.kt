package com.itzik.ui_layer.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itzik.commons.datamodels.BankData
import com.itzik.commons.datamodels.BankInfoData
import com.itzik.ui_layer.R

class BankTimeSeriesAdapter(bankInfoData:  ArrayList<BankInfoData> = ArrayList()) :
    RecyclerView.Adapter<BankTimeSeriesAdapter.BankViewHolder>() {


    private var dataList: ArrayList<BankInfoData> = bankInfoData


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        var view: View = layoutInflater.inflate(
            R.layout.item_info_bank, parent, false)
        Log.d(TAG, "onCreateViewHolder")
        return BankViewHolder(view)
    }


    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        var bankInfoData: BankInfoData = dataList[position]
        Log.d(TAG, "onBindViewHolder: bankInfoData - $bankInfoData")
        holder.bind(bankInfoData)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateBankInfoList(newbankList: List<BankInfoData>) {
        dataList.clear()
        dataList.addAll(newbankList)
        notifyDataSetChanged()
    }

    class BankViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bankView: View = view
        fun bind(bankInfoData: BankInfoData) {
            bankView.findViewById<TextView>(R.id.time).text = "${bankInfoData.time}"
            bankView.findViewById<TextView>(R.id.open).text = "${bankInfoData.open}"
            bankView.findViewById<TextView>(R.id.high).text = "${bankInfoData.hight}"
            bankView.findViewById<TextView>(R.id.low).text = "${bankInfoData.low}"
            bankView.findViewById<TextView>(R.id.close).text = "${bankInfoData.close}"
            bankView.findViewById<TextView>(R.id.volume).text = "${bankInfoData.volume}"
        }
    }

    companion object {
        val TAG = BankAdapter::class.java.simpleName
    }

}