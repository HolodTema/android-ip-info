package com.terabyte.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.terabyte.data.local.database.AppDatabase.Companion.DB_VERSION

@Database(entities = [IPInfoEntity::class], version =  DB_VERSION)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun ipInfoDao(): IPInfoDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "roomDB"
    }
}