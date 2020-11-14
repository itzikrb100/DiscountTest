package com.itzik.ui_layer.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.itzik.commons.datamodels.BankData
import com.itzik.ui_layer.R
import com.itzik.ui_layer.viewmodels.BankViewModel

class BankAdapter(bankViewModel: BankViewModel, banklist: ArrayList<BankData> = ArrayList()) :
    RecyclerView.Adapter<BankAdapter.BankViewHolder>() {


    private var dataList: ArrayList<BankData> = banklist
    private var viewModel: BankViewModel = bankViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        var view: View = layoutInflater.inflate(
            R.layout.item_bank, parent, false)
        Log.d(TAG, "onCreateViewHolder")
        return BankViewHolder(view)
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        var bankData: BankData = dataList[position]
        Log.d(TAG, "onBindViewHolder: bank data - $bankData")
        holder.bind(bankData, viewModel)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    fun updateBankList(newbankList: List<BankData>) {
        dataList.clear()
        dataList.addAll(newbankList)
        notifyDataSetChanged()
    }

    class BankViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bankView: View = view
        fun bind(bankData: BankData, viewModel: BankViewModel) {
            bankView.findViewById<TextView>(R.id.name).text = "${bankData.name}"
            bankView.findViewById<TextView>(R.id.stk).text = "${bankData.stk}"
            bankView.findViewById<TextView>(R.id.priority).text = "${bankData.priority}"
            Glide.with(bankView.context).load(bankData.imgUrl).into(bankView.findViewById(R.id.pic))
            bankView.setOnClickListener { viewModel.onClickItem(bankData) }
        }
    }

    companion object {
        val TAG = BankAdapter::class.java.simpleName
    }
}



