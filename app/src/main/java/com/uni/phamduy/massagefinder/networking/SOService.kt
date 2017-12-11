package com.uni.phamduy.coinmarket.networking


import com.uni.phamduy.massagefinder.module.district.District
import com.uni.phamduy.massagefinder.module.place.Place
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Created by PhamDuy on 3/28/2017.
 */

interface SOService {
    @GET("districts")
    fun getDistrict(@Query("city") city: String,
                @Header("x-app-token") user_id: String): Call<List<District>>

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
                    @Header("x-app-token") user_id: String): Call<List<Place>>
}
