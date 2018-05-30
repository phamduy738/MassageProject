package com.uni.phamduy.massagefinder.module.district

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Image() : Parcelable {

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

    companion object CREATOR : Parcelable.Creator<Image> {
        override fun createFromParcel(parcel: Parcel): Image {
            return Image(parcel)
        }

        override fun newArray(size: Int): Array<Image?> {
            return arrayOfNulls(size)
        }
    }

}
