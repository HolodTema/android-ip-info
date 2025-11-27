package com.terabyte.data.repository

import android.content.Context
import com.terabyte.data.local.database.DatabaseStorage
import com.terabyte.data.local.database.DatabaseStorageImpl
import com.terabyte.data.local.database.IPInfoEntity
import com.terabyte.domain.model.IPInfo
import com.terabyte.domain.repository.IPInfoRepository

class IPInfoRepositoryImpl(context: Context) : IPInfoRepository {
    private val databaseStorage: DatabaseStorage = DatabaseStorageImpl(context)

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