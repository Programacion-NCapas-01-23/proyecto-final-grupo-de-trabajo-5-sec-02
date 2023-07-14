package com.code_of_duty.u_tracker.data.database.entities

import androidx.room.Entity

@Entity(tableName = "subject_x_term", primaryKeys = ["personalTermId", "subjectCode"])
data class PersonalTermSubjectCrossRef(
    val personalTermId: String,
    val subjectCode: String
)
