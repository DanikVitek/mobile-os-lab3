package me.danikvitek.lab3.data

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun dateToLong(timestamp: Date): Long = timestamp.time

    @TypeConverter
    fun longToDate(long: Long): Date = Date(long)
}