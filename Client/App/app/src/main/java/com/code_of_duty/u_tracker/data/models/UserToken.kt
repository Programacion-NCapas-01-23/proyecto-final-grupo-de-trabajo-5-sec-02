package com.code_of_duty.u_tracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_token")
data class UserToken(
    @PrimaryKey
    val token: String
)
