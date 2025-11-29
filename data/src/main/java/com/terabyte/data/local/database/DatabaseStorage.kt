package com.terabyte.data.local.database

interface DatabaseStorage {

    suspend fun getAllIPInfo(): List<IPInfoEntity>

    suspend fun insert(ipInfo: IPInfoEntity)

    suspend fun delete(ipInfo: IPInfoEntity)

    suspend fun deleteAll()

}