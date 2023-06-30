package com.code_of_duty.u_tracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserToken(
    @PrimaryKey
    val token: String
)
