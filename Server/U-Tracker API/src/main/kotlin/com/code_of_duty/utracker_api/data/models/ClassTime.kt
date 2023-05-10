package com.code_of_duty.utracker_api.data.models

import com.code_of_duty.utracker_api.data.enums.Days
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalTime

@Entity
@Table(name = "class_time")
data class ClassTime(
    @Id
    val id: Int,
    val day: Days,
    val startHour: LocalTime,
    val totalHours: Int,
)
