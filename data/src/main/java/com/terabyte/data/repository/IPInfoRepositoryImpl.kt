package com.terabyte.data.repository

import android.content.Context
import com.terabyte.data.local.database.DatabaseStorage
import com.terabyte.data.local.database.DatabaseStorageImpl
import com.terabyte.data.local.database.IPInfoEntity
import com.terabyte.data.remote.gson.IPInfoJson
import com.terabyte.data.remote.retrofit.NetworkStorage
import com.terabyte.data.remote.retrofit.NetworkStorageImpl
import com.terabyte.domain.model.IPInfo
import com.terabyte.domain.repository.IPInfoRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

class IPInfoRepositoryImpl(context: Context) : IPInfoRepository {
    private val databaseStorage: DatabaseStorage = DatabaseStorageImpl(context)
    private val networkStorage: NetworkStorage = NetworkStorageImpl()

    override suspend fun getAllIpInfo(): List<IPInfo> {
        return databaseStorage.getAllIPInfo().map {
            mapToIPInfo(it)
        }
    }

    override suspend fun insertIpInfo(ipInfo: IPInfo) {
        databaseStorage.insert(mapToIPInfoEntity(ipInfo))
    }

    override suspend fun deleteIpInfo(ipInfo: IPInfo) {
        databaseStorage.delete(mapToIPInfoEntity(ipInfo))
    }

    override suspend fun requestIpInfo(ip: String): IPInfo? {
        val ipInfoJson = networkStorage.requestIpInfo(ip)
        if (ipInfoJson == null) {
            return null
        }
        val ipInfoEntity = mapToIPInfoEntity(ipInfoJson)
        databaseStorage.insert(ipInfoEntity)
        return mapToIPInfo(ipInfoEntity)
    }

    override suspend fun deleteIpInfoHistory() {
        databaseStorage.deleteAll()
    }

    private fun mapToIPInfoEntity(ipInfo: IPInfo): IPInfoEntity {
        return IPInfoEntity(
            id = ipInfo.id,
            ip = ipInfo.ip,
            hostname = ipInfo.hostname,
            country =  ipInfo.country,
            region = ipInfo.region,
            city = ipInfo.city,
            organization = ipInfo.organization,
            timezone =  ipInfo.timezone,
            latitude = ipInfo.latitude,
            longitude = ipInfo.longitude,
            infoDate = ipInfo.infoDate
        )
    }

    private fun mapToIPInfoEntity(ipInfo: IPInfoJson): IPInfoEntity {
        var latitude: Double? = null
        var longitude: Double? = null

        try {
            latitude = ipInfo.location?.split(", ")[0]?.toDouble()
            longitude = ipInfo.location?.split(", ")[1]?.toDouble()
        }
        catch (e: Exception) {

        }

        return IPInfoEntity(
            id = UUID.randomUUID(),
            ip = ipInfo.ip,
            hostname = ipInfo.hostname,
            country =  ipInfo.country,
            region = ipInfo.region,
            city = ipInfo.city,
            organization = ipInfo.organization,
            timezone =  ipInfo.timezone,
            latitude = latitude,
            longitude = longitude,
            infoDate = Date()
        )
    }


    private fun mapToIPInfo(ipInfo: IPInfoEntity): IPInfo {
        return IPInfo(
            id = ipInfo.id,
            ip = ipInfo.ip,
            hostname = ipInfo.hostname,
            country =  ipInfo.country,
            region = ipInfo.region,
            city = ipInfo.city,
            organization = ipInfo.organization,
            timezone =  ipInfo.timezone,
            latitude = ipInfo.latitude,
            longitude = ipInfo.longitude,
            infoDate = ipInfo.infoDate
        )
    }


}