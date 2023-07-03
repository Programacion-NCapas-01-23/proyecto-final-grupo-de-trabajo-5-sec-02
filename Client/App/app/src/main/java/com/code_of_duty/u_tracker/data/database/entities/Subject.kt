package com.code_of_duty.u_tracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subject")
data class Subject(
    @PrimaryKey
    val code: String,
    val name: String,
    val order: Int,
    val uv: Int,
    val estimateGrade: Float,
    val cycle: Int,
)
