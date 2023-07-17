package com.code_of_duty.u_tracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cycle_table")
data class Cycle(
    @PrimaryKey
    val id: Int,
    var name: String,
    val orderValue: Int,
    var cycleType: Int,
)