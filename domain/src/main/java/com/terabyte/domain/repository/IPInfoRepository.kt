package com.terabyte.domain.repository

import com.terabyte.domain.model.IPInfo

interface IPInfoRepository {

    suspend fun getAllIpInfo(): List<IPInfo>

    suspend fun insertIpInfo(ipInfo: IPInfo)

    suspend fun deleteIpInfo(ipInfo: IPInfo)

    suspend fun requestIpInfo(ip: String): IPInfo?

}