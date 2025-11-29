package com.terabyte.data.remote.retrofit

import com.terabyte.data.remote.gson.IPInfoJson

interface NetworkStorage {
    suspend fun requestIpInfo(ip: String): IPInfoJson?
}