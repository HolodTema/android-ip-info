package com.terabyte.data.remote.retrofit

import com.terabyte.data.remote.gson.IPInfoJson
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("{ip}/json")
    suspend fun getIpInfo(@Path("ip") ip: String): IPInfoJson

}