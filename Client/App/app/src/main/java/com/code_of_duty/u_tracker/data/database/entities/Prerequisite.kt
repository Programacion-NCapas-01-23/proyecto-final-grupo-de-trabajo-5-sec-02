package com.code_of_duty.u_tracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prerequisite")
data class Prerequisite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val subjectCode: String,
    val prerequisiteCode: Int,
)
