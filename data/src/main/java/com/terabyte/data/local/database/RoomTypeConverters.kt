package com.terabyte.data.local.database

import androidx.room.TypeConverter
import java.util.Date
import java.util.UUID

class RoomTypeConverters {
    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun toUUID(uuidStr: String?): UUID? {
        return UUID.fromString(uuidStr)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timeMills: Long?): Date? {
        if (timeMills != null) {
            return Date(timeMills)
        }
        return null
    }
}