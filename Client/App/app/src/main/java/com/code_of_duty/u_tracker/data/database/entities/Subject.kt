package com.code_of_duty.u_tracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subject")
data class Subject(
    @PrimaryKey
    val code: String,
    var name: String,
    var order: Int,
    var uv: Int,
    var cycle: Int,
)
