package com.uni.phamduy.coinmarket.networking

/**
 * Created by PhamDuy on 3/28/2017.
 */

object ApiUtils {


    val BASE_URL = "http://192.168.1.2:8080/.rest/api/v1/"
    val soService: SOService
        get() = RetrofitClient.getClient(BASE_URL)!!.create(SOService::class.java)
}
