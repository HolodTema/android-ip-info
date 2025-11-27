package com.terabyte.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IPInfoDao {

    @Insert
    fun insert(ipInfo: IPInfoEntity)

    @Delete
    fun delete(ipInfo: IPInfoEntity)

    @Query("SELECT * FROM ip_infos")
    fun getAll(): List<IPInfoEntity>
}