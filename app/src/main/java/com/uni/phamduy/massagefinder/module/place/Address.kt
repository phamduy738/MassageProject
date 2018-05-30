package com.uni.phamduy.massagefinder.module.place

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Address() : Parcelable {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("street")
    @Expose
    var street: String? = null
    @SerializedName("district")
    @Expose
    var district: String? = null
    @SerializedName("districtName")
    @Expose
    var districtName: String? = null
    @SerializedName("city")
    @Expose
    var city: String? = null
    @SerializedName("cityName")
    @Expose
    var cityName: String? = null
    @SerializedName("latitude")
    @Expose
    var latitude: String? = null
    @SerializedName("longitude")
    @Expose
    var longitude: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        street = parcel.readString()
        district = parcel.readString()
        districtName = parcel.readString()
        city = parcel.readString()
        cityName = parcel.readString()
        latitude = parcel.readString()
        longitude = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(street)
        parcel.writeString(district)
        parcel.writeString(districtName)
        parcel.writeString(city)
        parcel.writeString(cityName)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Address> {
        override fun createFromParcel(parcel: Parcel): Address {
            return Address(parcel)
        }

        override fun newArray(size: Int): Array<Address?> {
            return arrayOfNulls(size)
        }
    }

}
