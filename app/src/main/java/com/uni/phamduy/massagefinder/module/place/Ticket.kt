package com.uni.phamduy.massagefinder.module.place

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Ticket() : Parcelable {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("period")
    @Expose
    var period: Int? = null
    @SerializedName("price")
    @Expose
    var price: Int? = null
    @SerializedName("priceWithCode")
    @Expose
    var priceWithCode: Int? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("tip")
    @Expose
    var tip: Int? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        period = parcel.readValue(Int::class.java.classLoader) as? Int
        price = parcel.readValue(Int::class.java.classLoader) as? Int
        priceWithCode = parcel.readValue(Int::class.java.classLoader) as? Int
        description = parcel.readString()
        tip = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeValue(period)
        parcel.writeValue(price)
        parcel.writeValue(priceWithCode)
        parcel.writeString(description)
        parcel.writeValue(tip)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ticket> {
        override fun createFromParcel(parcel: Parcel): Ticket {
            return Ticket(parcel)
        }

        override fun newArray(size: Int): Array<Ticket?> {
            return arrayOfNulls(size)
        }
    }

}
