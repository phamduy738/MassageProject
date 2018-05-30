package com.uni.phamduy.massagefinder.module.place

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Place() : Parcelable {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("address")
    @Expose
    var address: Address? = null
    @SerializedName("review")
    @Expose
    var review: String? = null
    @SerializedName("rating")
    @Expose
    var rating: Int? = null
    @SerializedName("tickets")
    @Expose
    var tickets: List<Ticket>? = null
    @SerializedName("hotLines")
    @Expose
    var hotLines: List<String>? = null
    @SerializedName("tags")
    @Expose
    var tags: List<Any>? = null
    @SerializedName("coverImage")
    @Expose
    var coverImage: CoverImage? = null
    @SerializedName("slideImages")
    @Expose
    var slideImages: List<SlideImage>? = null
    @SerializedName("distance")
    @Expose
    var distance: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        address = parcel.readParcelable(Address::class.java.classLoader)
        review = parcel.readString()
        rating = parcel.readValue(Int::class.java.classLoader) as? Int
        tickets = parcel.createTypedArrayList(Ticket)
        hotLines = parcel.createStringArrayList()
        coverImage = parcel.readParcelable(CoverImage::class.java.classLoader)
        slideImages = parcel.createTypedArrayList(SlideImage)
        distance = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeParcelable(address, flags)
        parcel.writeString(review)
        parcel.writeValue(rating)
        parcel.writeTypedList(tickets)
        parcel.writeStringList(hotLines)
        parcel.writeParcelable(coverImage, flags)
        parcel.writeTypedList(slideImages)
        parcel.writeString(distance)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Place> {
        override fun createFromParcel(parcel: Parcel): Place {
            return Place(parcel)
        }

        override fun newArray(size: Int): Array<Place?> {
            return arrayOfNulls(size)
        }
    }


}
