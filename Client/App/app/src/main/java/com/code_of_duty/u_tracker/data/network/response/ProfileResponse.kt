package com.code_of_duty.u_tracker.data.network.response

import com.code_of_duty.u_tracker.data.database.entities.Cycle

data class Pensum (
    val id: String,
    val plan: String,
    )

data class Degree (
    val id: String,
    val name: String,
    val pensums: List<Pensum>?,
    )

data class ProfileResponse (
    val code: String,
    val name: String,
    val image: String?,
    val cum: Float,
    val degree: String,
    val approvedSubjects: Int,
    val pensum: String,
    val faculty: String,
)