package com.code_of_duty.u_tracker.ui.models

import com.code_of_duty.u_tracker.data.database.entities.Assesment

data class SubjectWithAssesment(
    val code: String,
    val name: String,
    var assesments: List<Assesment>
)
