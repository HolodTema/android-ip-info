package com.terabyte.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "ip_infos")
data class IPInfoEntity(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val ip: String,
    val hostname: String,
    val country: String,
    val region: String,
    val city: String,
    val organization: String,
    val timezone: String,
    val latitude: Double?,
    val longitude: Double?,
    val infoDate: Date
)