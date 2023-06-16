package com.code_of_duty.utracker_api.data.models

import com.code_of_duty.utracker_api.data.enums.Days
import jakarta.persistence.*
import java.time.LocalTime
import java.util.UUID

@Entity
@Table(name = "class_time")
data class ClassTime(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = UUID.randomUUID(),
    val day: Days,
    val startHour: LocalTime,
    val totalHours: Int,
)
