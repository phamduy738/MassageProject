package com.uni.phamduy.coinmarket.networking


import com.uni.phamduy.massagefinder.module.district.District
import com.uni.phamduy.massagefinder.module.place.Place
import com.uni.phamduy.massagefinder.module.review.Review
import com.uni.phamduy.massagefinder.module.staff.Staff
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by PhamDuy on 3/28/2017.
 */

interface SOService {
    @GET("districts")
    fun getDistrict(@Query("city") city: String,
                @Header("x-app-token") category: String): Call<List<District>>

    @GET("places")
    fun getPlace(@Query("city") city: String,
                 @Query("district") district: String,
                 @Query("tag") tag: String,
                 @Query("latitude") latitude: String,
                 @Query("longitude") longitude: String,
                 @Query("sortProperty") sortProperty: String,
                 @Query("sortOrder") sortOrder: String,
                 @Query("offset") offset:String,
                 @Query("limit") limit: String,
                 @Header("x-app-token") category: String): Call<List<Place>>
    @GET("staffs")
    fun getStaff(@Query("placeId") placeId: String,
                 @Query("sortProperty") sortProperty: String,
                 @Query("sortOrder") sortOrder: String,
                 @Query("offset") offset:String,
                 @Query("limit") limit: String,
                 @Header("x-app-token") category: String): Call<List<Staff>>
    @GET("places/{id}")
    fun getPlaceDetail(@Path("id") id: String,
                       @Header("x-app-token") category: String): Call<Place>
    @GET("staffs/{id}")
    fun getStaffDetail(@Path("id") id: String,
                       @Header("x-app-token") category: String): Call<Staff>
    @GET("reviews")
    fun getListReview(@Query("staffId") placeId: String,
                      @Query("sortProperty") sortProperty: String,
                      @Query("sortOrder") sortOrder: String,
                      @Query("offset") offset:String,
                      @Query("limit") limit: String,
                      @Header("x-app-token") category: String): Call<List<Review>>
}
