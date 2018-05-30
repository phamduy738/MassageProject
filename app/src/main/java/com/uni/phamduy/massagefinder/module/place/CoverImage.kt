package com.uni.phamduy.massagefinder.module.place

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CoverImage() : Parcelable {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("link")
    @Expose
    var link: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        link = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(link)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CoverImage> {
        override fun createFromParcel(parcel: Parcel): CoverImage {
            return CoverImage(parcel)
        }

        override fun newArray(size: Int): Array<CoverImage?> {
            return arrayOfNulls(size)
        }
    }

}
