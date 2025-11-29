package com.terabyte.data.local.database

import android.content.Context
import androidx.room.Room
import com.terabyte.data.local.database.AppDatabase.Companion.DB_NAME


class DatabaseStorageImpl(context: Context) : DatabaseStorage {
    val db = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
        .build()

    override suspend fun getAllIPInfo(): List<IPInfoEntity> {
        return db.ipInfoDao().getAll()
    }

    override suspend fun insert(ipInfo: IPInfoEntity) {
        db.ipInfoDao().insert(ipInfo)
    }

    override suspend fun delete(ipInfo: IPInfoEntity) {
        db.ipInfoDao().delete(ipInfo)
    }

    override suspend fun deleteAll() {
        db.ipInfoDao().deleteAll()
    }
}