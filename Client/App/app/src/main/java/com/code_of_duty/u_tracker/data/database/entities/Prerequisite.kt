package com.code_of_duty.u_tracker.data.database.entities

import androidx.room.Entity

@Entity
data class Prerequisite(
    val subjectCode: String,
    val prerequisiteCode: Int,
)
