package com.code_of_duty.u_tracker.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "personal_term_table")
data class PersonalTerm(
    @PrimaryKey
    val id: String,
    val studentCode: String,
    val cycleType: Int,
    val year: Int,
)
