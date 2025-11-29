package com.terabyte.data.remote.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import com.terabyte.data.LOG_TAG
import com.terabyte.data.remote.gson.IPInfoJson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkStorageImpl : NetworkStorage {
    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .serializeNulls()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://ipinfo.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitService: RetrofitService by lazy {
        retrofit.create(RetrofitService::class.java)
    }

    override suspend fun requestIpInfo(ip: String): IPInfoJson? {
        return try {
            retrofitService.getIpInfo(ip)
        }
        catch(e: Exception) {
            Log.d(LOG_TAG, e.message.toString())
            null
        }
    }

}