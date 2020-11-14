package com.itzik.commons.datamodels

import android.os.Parcel
import android.os.Parcelable

data class BankData (val name: String?, val stk: String?, val imgUrl: String?, val priority: Int): Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeString(stk)
        dest?.writeString(imgUrl)
        dest?.writeInt(priority)
    }

    companion object CREATOR : Parcelable.Creator<BankData> {
        override fun createFromParcel(parcel: Parcel): BankData {
            return BankData(parcel)
        }

        override fun newArray(size: Int): Array<BankData?> {
            return arrayOfNulls(size)
        }
    }
}