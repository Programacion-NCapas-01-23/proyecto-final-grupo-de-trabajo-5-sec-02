package com.code_of_duty.u_tracker.data.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> = value.split(",").map { it }

    @TypeConverter
    fun toString(value: MutableList<String>): String = value.joinToString(",")
}