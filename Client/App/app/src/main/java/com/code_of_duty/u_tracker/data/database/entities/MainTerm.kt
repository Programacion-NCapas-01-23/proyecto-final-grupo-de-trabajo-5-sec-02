package com.code_of_duty.u_tracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MainTerm(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val cycleType: Int,
    val year: Int
)
