package com.uni.phamduy.massagefinder.module.district

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class District() : Parcelable {

    @SerializedName("district")
    @Expose
    var district: String? = null
    @SerializedName("districtName")
    @Expose
    var districtName: String? = null
    @SerializedName("image")
    @Expose
    var image: Image? = null

    constructor(parcel: Parcel) : this() {
        district = parcel.readString()
        districtName = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(district)
        parcel.writeString(districtName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<District> {
        override fun createFromParcel(parcel: Parcel): District {
            return District(parcel)
        }

        override fun newArray(size: Int): Array<District?> {
            return arrayOfNulls(size)
        }
    }

}
